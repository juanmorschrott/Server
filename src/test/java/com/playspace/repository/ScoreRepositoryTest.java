package com.playspace.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ScoreRepositoryTest {

	private ScoreRepository scoreRepository;
	
	@Before
	public void init() {
		scoreRepository = ScoreRepository.getInstance();
	}
	
	@Test
	public void find_highest_score_by_level_id_test() {
		String csv = scoreRepository.findHighestScoresByLevelId(2);
		assertEquals(csv, "4711=57456,131=57456");
	}

}
