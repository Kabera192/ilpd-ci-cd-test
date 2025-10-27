package rw.ac.ilpd.sharedlibrary.util;

/**
 * Centralized class for all upload path constants used across the application.
 */
    public final class ObjectUploadPath {
        public static final class Academic {
            public static final String BASE = "Academic";
            public static final String COURSE_MATERIAL_PATH = "academic-document/course-material";
            public static final String ACADEMIC_APPLICATION_DOCUMENT_PATH = "academic-document/ac_application";
        }
    public static final class User {
        public static final String BASE = "User";
        public static final String USER_DOCUMENT_PATHS = "user-docs";
        public static final String WORKING_EXPERIENCE = "users-doc/work-experience";
        public static final String PROFILE = "users/profile";

    }
    public static final class Announcement {
        public static final String BASE = "Announcement";
        public static final String ATTACHMENT = "announcement/attachment";
    }

    public static final class Attendance {
        public static final String BASE = "Attendance";
        public static final String RECORD = "attendance-document/attendance-record";
        public static final String ABSENCE_JUSTIFICATION = "attendance-document/attendance-absence-justification";
    }

    public static final class Course {
        public static final String BASE = "Course";
        public static final String MATERIAL = "learning-platform/course/material-docs";

        public static final class Assignment {
            public static final String BASE = "Assignment";
            public static final String INDIVIDUAL = "learning-platform/course/assignment/individual";
            public static final String GROUP = "learning-platform/course/assignment/group";
            public static final String SUBMISSION_INDIVIDUAL = "learning-platform-document/course/assignment/submission/individual";
            public static final String SUBMISSION_GROUP = "learning-platform-document/course/assignment/submission/group";
        }

        public static final class Examination {
            public static final String BASE = "Examination";
            public static final String DOCUMENTS = "learning-platform-document/course/examination/examination-docs";
            public static final String SUBMISSION = "learning-platform-document/course/examination/submission-docs";
        }

        public static final class Research {
            public static final String BASE = "Research";
            public static final String EXERCISE_DOCUMENTS = "learning-platform-document/course/research/exercise/exercise-docs";
            public static final String SUBMISSION = "learning-platform-document/research/exercise/submission-docs";
        }
    }

    public static final class Finance {
        public static final String BASE = "Finance";
        public static final String ATTACHMENT = "finance-document/payment/attachment";
        public static final String ACCOMMODATION_FEES = "finance-document/payment/accommodation-fees";
        public static final String DAMAGES_FEES = "finance-document/payment/damages-fees";
        public static final String GRADUATION_FEES = "finance-document/payment/graduation-fees";
        public static final String LIBRARY_FEES = "finance-document/payment/library-fees";
        public static final String OTHERS_FEES = "finance-document/payment/other-fees";
        public static final String REFUND = "finance-document/payment/refund-request-docs";
        public static final String RESTAURANT_FEES = "finance-document/payment/restaurant-fees";
        public static final String RETAKE_FEES = "finance-document/payment/retake-fees-docs";
        public static final String TUITION_FEES = "finance-document/payment/tuition-fees";
    }

    public static final class Institution {
        public static final String BASE = "Announcement";
        public static final String PROFILE_INFORMATION = "institution/profile_information_files";
    }

    public static final class Internship {
        public static final String BASE = "Internship";
        public static final String DOCUMENTS = "learning-platform-document/internship/internship-docs";
        public static final String APPEAL = "learning-platform-document/internship/appeal-docs";
    }

    public static final class StudentApplication {
        public static final String BASE = "StudentApplication";
        public static final String DEGREE = "application-documents/degree";
        public static final String FEES = "application-documents/application-fees";
        public static final String IDENTIFICATION = "application-documents/identification";
        public static final String OTHERS = "application-documents/others";
        public static final String PROFILE_PICTURE = "application-documents/profile-picture";
        public static final String TRANSCRIPT = "application-documents/transcript";
    }

    public static final class Hostel {
        public static final String BASE = "Hostel";
        public static final String ROOM_TYPE_IMAGE = BASE.toLowerCase()+"/room-images";
    }
}
