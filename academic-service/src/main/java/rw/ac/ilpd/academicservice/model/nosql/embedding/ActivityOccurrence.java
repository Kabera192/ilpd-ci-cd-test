package rw.ac.ilpd.academicservice.model.nosql.embedding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class ActivityOccurrence {
    private String id = UUID.randomUUID().toString();
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID lecturerId;
    private Boolean hasDoneAttendance;
    private Boolean deletedStatus = false;
    List<AttendanceMissing> attendanceMissings;


    public ActivityOccurrence(String id, LocalDate day, LocalTime startTime, LocalTime endTime, UUID lecturerId, Boolean hasDoneAttendance, Boolean deletedStatus, List<AttendanceMissing> attendanceMissings) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lecturerId = lecturerId;
        this.hasDoneAttendance = hasDoneAttendance;
        this.deletedStatus = deletedStatus;
        this.attendanceMissings = attendanceMissings;
    }

    public ActivityOccurrence() {
    }

    public static ActivityOccurrenceBuilder builder() {
        return new ActivityOccurrenceBuilder();
    }

    public String getId() {
        return this.id;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public UUID getLecturerId() {
        return this.lecturerId;
    }

    public Boolean getHasDoneAttendance() {
        return this.hasDoneAttendance;
    }

    public Boolean getDeletedStatus() {
        return this.deletedStatus;
    }

    public List<AttendanceMissing> getAttendanceMissings() {
        return this.attendanceMissings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setLecturerId(UUID lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setHasDoneAttendance(Boolean hasDoneAttendance) {
        this.hasDoneAttendance = hasDoneAttendance;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public void setAttendanceMissings(List<AttendanceMissing> attendanceMissings) {
        this.attendanceMissings = attendanceMissings;
    }

    public static class ActivityOccurrenceBuilder {
        private String id;
        private LocalDate day;
        private LocalTime startTime;
        private LocalTime endTime;
        private UUID lecturerId;
        private Boolean hasDoneAttendance;
        private Boolean deletedStatus;
        private List<AttendanceMissing> attendanceMissings;

        ActivityOccurrenceBuilder() {
        }

        public ActivityOccurrenceBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ActivityOccurrenceBuilder day(LocalDate day) {
            this.day = day;
            return this;
        }

        public ActivityOccurrenceBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public ActivityOccurrenceBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public ActivityOccurrenceBuilder lecturerId(UUID lecturerId) {
            this.lecturerId = lecturerId;
            return this;
        }

        public ActivityOccurrenceBuilder hasDoneAttendance(Boolean hasDoneAttendance) {
            this.hasDoneAttendance = hasDoneAttendance;
            return this;
        }

        public ActivityOccurrenceBuilder deletedStatus(Boolean deletedStatus) {
            this.deletedStatus = deletedStatus;
            return this;
        }

        public ActivityOccurrenceBuilder attendanceMissings(List<AttendanceMissing> attendanceMissings) {
            this.attendanceMissings = attendanceMissings;
            return this;
        }

        public ActivityOccurrence build() {
            return new ActivityOccurrence(this.id, this.day, this.startTime, this.endTime, this.lecturerId, this.hasDoneAttendance, this.deletedStatus, this.attendanceMissings);
        }

        public String toString() {
            return "ActivityOccurrence.ActivityOccurrenceBuilder(id=" + this.id + ", day=" + this.day + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", lecturerId=" + this.lecturerId + ", hasDoneAttendance=" + this.hasDoneAttendance + ", deletedStatus=" + this.deletedStatus + ", attendanceMissings=" + this.attendanceMissings + ")";
        }
    }
}
