package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@RequiredArgsConstructor
public class StatusSeeder {

    private final StatusRepository statusRepository;

    /*
    Initiate and return animal statuses. All statuses except the last one contain a start and end date.
    The last status contains only information about the start date. This means that the status is still ongoing.
    When one status ends, the next one begins, e.g.
    Status     |    BeginningDate    |    EndDate
    first      |         X           |       Y
    second     |         Y           |       Z
    third      |         Z           |      null
     */
    public Set<Status> initAnimalStatuses(Date beginDate, AnimalStatus... animalStatuses) {
        int statusesAmount = animalStatuses.length;
        LinkedList<Date> datesBetweenStatuses = generateDatesBetween(beginDate, new Date(), statusesAmount - 1);
        List<Status> statuses = new ArrayList<>();
        Date previousDate = beginDate;
        for (int i = 0; i < statusesAmount; i++) {
            AnimalStatus status = animalStatuses[i];
            if (i == 0 && statusesAmount > 1) {
                previousDate = datesBetweenStatuses.pop();
                statuses.add(new Status(status, beginDate, previousDate));
            } else if (i == 0) {
                statuses.add(new Status(status, beginDate));
            } else if (i == statusesAmount - 1) {
                statuses.add(new Status(status, previousDate));
            } else {
                statuses.add(new Status(status, previousDate, datesBetweenStatuses.getFirst()));
                previousDate = datesBetweenStatuses.pop();
            }
        }
        return initStatuses(statuses);
    }

    private LinkedList<Date> generateDatesBetween(Date startDate, Date endDate, int datesAmount) {
        int daysBetweenBeginAndEnd = Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();
        LinkedList<Date> dates = new LinkedList<>();
        DateTime lastDate = new DateTime(startDate);
        for (int i = 0; i < datesAmount; i++) {
            int daysToAdd = generateRandomNumberOfDays(daysBetweenBeginAndEnd);
            DateTime generatedDate = lastDate.plusDays(daysToAdd);
            dates.addLast(generatedDate.toDate());
            daysBetweenBeginAndEnd -= daysToAdd;
        }
        return dates;
    }

    private int generateRandomNumberOfDays(int maxNumber) {
        return new Random().nextInt(maxNumber);
    }

    @Transactional
    private Set<Status> initStatuses(List<Status> statuses) {
        return StreamSupport.stream(
                statusRepository.saveAll(statuses).spliterator(), false)
                .collect(Collectors.toSet());
    }

}
