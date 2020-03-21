package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.repository.TreatmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.inject.Singleton;
import java.util.Date;
import java.util.Random;

@Singleton
@RequiredArgsConstructor
public class TreatmentHistorySeeder {

	private final TreatmentHistoryRepository treatmentHistoryRepository;


	private Date getRandomDateBetween(Date startDate, Date endDate) {
		DateTime startDateTime = new DateTime(startDate);
		int daysBetweenBeginAndEnd = Days.daysBetween(startDateTime, new DateTime(endDate)).getDays();
		return startDateTime.plusDays(generateRandomNumberOfDays(daysBetweenBeginAndEnd)).toDate();
	}

	private int generateRandomNumberOfDays(int maxNumber) {
		return new Random().nextInt(maxNumber);
	}

}
