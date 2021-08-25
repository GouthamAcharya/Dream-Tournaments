package com.dreamtournaments.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamtournaments.exception.SportsClubNotFoundException;
import com.dreamtournaments.models.SportsClub;
import com.dreamtournaments.models.SportsClubRegistration;
import com.dreamtournaments.repository.ISportsClubRepository;

@Service
public class SportsClubServiceImpl implements ISportsClubService {

	@Autowired
	ISportsClubRepository iSportsClubRepository;

	@Override
	public void postSportsClub(SportsClub sportsClub) {

		iSportsClubRepository.insert(sportsClub);
	}

	@Override
	public void deleteSportsClub(String sportsClubId) {

		if (iSportsClubRepository.existsById(sportsClubId))
			iSportsClubRepository.deleteById(sportsClubId);
		else
			throw new SportsClubNotFoundException("No sports club found with this id");
	}

	@Override
	public void updateSportsClub(SportsClub sportsClub) {

		iSportsClubRepository.save(sportsClub);
	}

	@Override
	public void registerForSportsClub(SportsClubRegistration sportsClubRegistration, String sportsClubId) {

		Optional<SportsClub> sportsClub = iSportsClubRepository.findById(sportsClubId);

		if (sportsClub.isEmpty())
			throw new SportsClubNotFoundException("invalid sports club id.");

		if (!(sportsClub.get().getRegistrations() == null))
			sportsClub.get().getRegistrations().add(sportsClubRegistration);
		else {
			List<SportsClubRegistration> sportsClubRegistrationsList = new ArrayList<>();
			sportsClubRegistrationsList.add(sportsClubRegistration);
			sportsClub.get().setRegistrations(sportsClubRegistrationsList);
		}

		iSportsClubRepository.save(sportsClub.get());
	}

	@Override
	public void updateSportsClubStatus(String sportsClubId, String postStatus) {

		Optional<SportsClub> sportsClub = iSportsClubRepository.findById(sportsClubId);

		if (sportsClub.isEmpty())
			throw new SportsClubNotFoundException("Sports club not found.");

		sportsClub.get().setPostStatus(postStatus);
		iSportsClubRepository.save(sportsClub.get());
	}

	@Override
	public List<SportsClub> getSportsClubByPostStaus(String postStaus) {

		List<SportsClub> sportsClub = iSportsClubRepository.findByPostStatus(postStaus);

		if (sportsClub.isEmpty())
			throw new SportsClubNotFoundException("No sports club found with status " + postStaus);

		return sportsClub;
	}

	@Override
	public List<SportsClub> getSportsClubByRegex(String searchString) {

		List<SportsClub> sportsClubs = iSportsClubRepository.findBySportsClubByRegex(searchString);

		if (sportsClubs.isEmpty())
			throw new SportsClubNotFoundException("No sports clubs found with this title.");

		return sportsClubs;
	}

	@Override
	public List<SportsClubRegistration> getSportsClubRegistrations(String sportsClubId) {

		Optional<SportsClub> sportsClub = iSportsClubRepository.findById(sportsClubId);

		if (sportsClub.isEmpty())
			throw new SportsClubNotFoundException("invalid sports club id.");

		List<SportsClubRegistration> sportsClubRegistrations = sportsClub.get().getRegistrations();
		return sportsClubRegistrations;
	}

}
