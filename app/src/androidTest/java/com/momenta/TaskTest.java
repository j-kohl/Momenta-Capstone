package com.momenta;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;

/**
 * Unit Test for class task
 */
public class TaskTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    public final String TASK_NAME = "TASK_NAME";

    @Test
    public void testTaskGoal() {
        Task task = new Task(TASK_NAME, 60, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        assertEquals("1H", task.getFormattedGoal());

        task = new Task(TASK_NAME, 0, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        assertEquals("", task.getFormattedGoal());

        task = new Task("Test task", 61, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        assertEquals("1H 1M", task.getFormattedGoal());
    }

    @Test
    public void testAddMinute() {
        Task task = new Task(TASK_NAME, 0, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        task.addTimeInMinutes(310);
        assertEquals(310, task.getTimeSpent());

        task = new Task(TASK_NAME, 30, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        task.addTimeInMinutes(30);
        assertEquals(30, task.getTimeSpent());

        task = new Task(TASK_NAME, 61, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        task.addTimeInMinutes(25);
        assertEquals(25, task.getTimeSpent());
    }

    @Test
    public void testNegativeNumbers() {
        exception.expect(IllegalArgumentException.class);
        new Task(TASK_NAME, -1, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());

        Task task = new Task(TASK_NAME, -0, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        exception.expect(IllegalArgumentException.class);
        task.addTimeInMinutes(-30);
    }

    @Test
    public void testGetTime() {
        Task task = new Task(TASK_NAME, 198, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());
        task.setGoalInMinutes(434);
        assertEquals(434, task.getGoalInMinutes());
    }

    @Test
    public void testDeadlineCalendarAccessModifiers() {
        Calendar cal = Calendar.getInstance();
        Task task = new Task(TASK_NAME, 198, cal,
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());

        cal.set( 2022, 11, 10);
        task.setDeadline(cal);
        assertEquals( cal, task.getDeadline());

        cal.set( 1850, 1, 1);
        task.setDeadline(cal);
        assertEquals( cal, task.getDeadline());

        cal.set(1990, 12, 31);
        task.setDeadline(cal);
        assertEquals(cal, task.getDeadline());

        cal.set(3098, 6, 30);
        task.setDeadline(cal);
        assertEquals( cal, task.getDeadline());
    }

    @Test
    public void testDateCreatedAccessModifiers() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 11, 10);
        Task task = new Task(TASK_NAME, 198, Calendar.getInstance(),
                cal.getTimeInMillis(), Calendar.getInstance());
        assertEquals(cal.getTimeInMillis(), task.getDateCreated());

        cal.set(1850, 1, 1);
        task = new Task(TASK_NAME, 198, Calendar.getInstance(),
                cal.getTimeInMillis(), Calendar.getInstance());
        assertEquals(cal.getTimeInMillis(), task.getDateCreated());

        cal.set( 1990, 12, 31);
        task = new Task(TASK_NAME, 198, Calendar.getInstance(),
                cal.getTimeInMillis(), Calendar.getInstance());
        assertEquals(cal.getTimeInMillis(), task.getDateCreated());

        cal.set( 3098, 6, 30);
        task = new Task(TASK_NAME, 198, Calendar.getInstance(),
                cal.getTimeInMillis(), Calendar.getInstance());
        assertEquals(cal.getTimeInMillis(), task.getDateCreated());
    }

    @Test
    public void testLastModifiedAccessModifiers() {
        Calendar cal = Calendar.getInstance();
        Task task = new Task(TASK_NAME, 198, cal,
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());

        cal.set( 2022, 11, 10);
        task.setLastModified(cal);
        assertEquals( cal, task.getLastModified());

        cal.set( 1850, 1, 1);
        task.setLastModified(cal);
        assertEquals( cal, task.getLastModified());

        cal.set( 1990, 12, 31);
        task.setLastModified(cal);
        assertEquals( cal, task.getLastModified());

        cal.set( 3098, 6, 30);
        task.setLastModified(cal);
        assertEquals( cal, task.getLastModified());
    }

    @Test
    public void testPriorityPriorityAccessModifiers() {
        Task task = new Task(TASK_NAME, 198, Calendar.getInstance(),
                Calendar.getInstance().getTimeInMillis(), Calendar.getInstance());

        task.setPriority(Task.Priority.VERY_LOW);
        assertEquals(Task.Priority.VERY_LOW, task.getPriority());

        task.setPriority(Task.Priority.LOW);
        assertEquals(Task.Priority.LOW, task.getPriority());

        task.setPriority(Task.Priority.MEDIUM);
        assertEquals(Task.Priority.MEDIUM, task.getPriority());

        task.setPriority(Task.Priority.HIGH);
        assertEquals(Task.Priority.HIGH, task.getPriority());

        task.setPriority(Task.Priority.VERY_HIGH);
        assertEquals(Task.Priority.VERY_HIGH, task.getPriority());
    }

    @Test
    public void testStripNonDigits() {
        assertEquals("232221322302382",Task.stripNonDigits("232idfdbld22132ldifdbf2302382ldffib"));
        assertEquals("10030", Task.stripNonDigits("100H30M"));
        assertEquals("2082", Task.stripNonDigits("Rea2082RE"));
        assertEquals( "000030", Task.stripNonDigits("-00H0030M"));
    }
}
