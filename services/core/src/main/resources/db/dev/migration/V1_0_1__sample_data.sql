CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Inserting base categories
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Academics', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Professional Development', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Languages', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Test Preparation', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Hobbies & Interests', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Entertainment', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Life Skills', NULL),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Miscellaneous', NULL);

-- Inserting subcategories for 'Academics'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Science', (SELECT uuid FROM quiz_category WHERE name='Academics')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Mathematics', (SELECT uuid FROM quiz_category WHERE name='Academics')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Language Arts', (SELECT uuid FROM quiz_category WHERE name='Academics')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Social Studies', (SELECT uuid FROM quiz_category WHERE name='Academics'));

-- Inserting subcategories for 'Science'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Physics', (SELECT uuid FROM quiz_category WHERE name='Science')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Chemistry', (SELECT uuid FROM quiz_category WHERE name='Science')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Biology', (SELECT uuid FROM quiz_category WHERE name='Science')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Earth Science', (SELECT uuid FROM quiz_category WHERE name='Science'));

-- Inserting subcategories for 'Mathematics'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Algebra', (SELECT uuid FROM quiz_category WHERE name='Mathematics')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Geometry', (SELECT uuid FROM quiz_category WHERE name='Mathematics')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Calculus', (SELECT uuid FROM quiz_category WHERE name='Mathematics')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Statistics', (SELECT uuid FROM quiz_category WHERE name='Mathematics'));

-- Inserting subcategories for 'Language Arts'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Grammar', (SELECT uuid FROM quiz_category WHERE name='Language Arts')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Literature', (SELECT uuid FROM quiz_category WHERE name='Language Arts')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Writing', (SELECT uuid FROM quiz_category WHERE name='Language Arts')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Vocabulary', (SELECT uuid FROM quiz_category WHERE name='Language Arts'));

-- Inserting subcategories for 'Social Studies'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'History', (SELECT uuid FROM quiz_category WHERE name='Social Studies')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Geography', (SELECT uuid FROM quiz_category WHERE name='Social Studies')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Economics', (SELECT uuid FROM quiz_category WHERE name='Social Studies')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Political Science', (SELECT uuid FROM quiz_category WHERE name='Social Studies'));

-- Inserting subcategories for 'Professional Development'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Business', (SELECT uuid FROM quiz_category WHERE name='Professional Development')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Technology', (SELECT uuid FROM quiz_category WHERE name='Professional Development')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Health & Medicine', (SELECT uuid FROM quiz_category WHERE name='Professional Development')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Education', (SELECT uuid FROM quiz_category WHERE name='Professional Development'));

-- Inserting subcategories for 'Business'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Marketing', (SELECT uuid FROM quiz_category WHERE name='Business')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Finance', (SELECT uuid FROM quiz_category WHERE name='Business')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Management', (SELECT uuid FROM quiz_category WHERE name='Business')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Entrepreneurship', (SELECT uuid FROM quiz_category WHERE name='Business'));

-- Inserting subcategories for 'Technology'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Information Technology', (SELECT uuid FROM quiz_category WHERE name='Technology')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Software Development', (SELECT uuid FROM quiz_category WHERE name='Technology')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Cybersecurity', (SELECT uuid FROM quiz_category WHERE name='Technology')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Data Science', (SELECT uuid FROM quiz_category WHERE name='Technology'));

-- Inserting subcategories for 'Health & Medicine'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Nursing', (SELECT uuid FROM quiz_category WHERE name='Health & Medicine')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Medical Terminology', (SELECT uuid FROM quiz_category WHERE name='Health & Medicine')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Nutrition', (SELECT uuid FROM quiz_category WHERE name='Health & Medicine')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Public Health', (SELECT uuid FROM quiz_category WHERE name='Health & Medicine'));

-- Inserting subcategories for 'Education'
INSERT INTO quiz_category (uuid, created_date, is_deleted, last_modified_date, name, parent_uuid)
VALUES
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Teaching Strategies', (SELECT uuid FROM quiz_category WHERE name='Education')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Curriculum Development', (SELECT uuid FROM quiz_category WHERE name='Education')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Educational Psychology', (SELECT uuid FROM quiz_category WHERE name='Education')),
    (uuid_generate_v4(), current_timestamp, false, NULL, 'Classroom Management', (SELECT uuid FROM quiz_category WHERE name='Education'));

-- Inserting tags
INSERT INTO quiz_tag (name, uuid, created_date, is_deleted)
VALUES
    ('Quick Quiz', uuid_generate_v4(), current_timestamp, false),
    ('Long Quiz', uuid_generate_v4(), current_timestamp, false),
    ('School', uuid_generate_v4(), current_timestamp, false),
    ('Pop Quiz', uuid_generate_v4(), current_timestamp, false),
    ('Formulas', uuid_generate_v4(), current_timestamp, false),
    ('Laws', uuid_generate_v4(), current_timestamp, false),
    ('Theories', uuid_generate_v4(), current_timestamp, false),
    ('Ecology', uuid_generate_v4(), current_timestamp, false),
    ('Genetics', uuid_generate_v4(), current_timestamp, false),
    ('Physics', uuid_generate_v4(), current_timestamp, false),
    ('Chemistry', uuid_generate_v4(), current_timestamp, false),
    ('Biology', uuid_generate_v4(), current_timestamp, false),
    ('Equations', uuid_generate_v4(), current_timestamp, false),
    ('Theorems', uuid_generate_v4(), current_timestamp, false),
    ('Geometry', uuid_generate_v4(), current_timestamp, false),
    ('Algebra', uuid_generate_v4(), current_timestamp, false),
    ('Trigonometry', uuid_generate_v4(), current_timestamp, false),
    ('Statistics', uuid_generate_v4(), current_timestamp, false),
    ('Probability', uuid_generate_v4(), current_timestamp, false),
    ('Grammar', uuid_generate_v4(), current_timestamp, false),
    ('Literature', uuid_generate_v4(), current_timestamp, false),
    ('Vocabulary', uuid_generate_v4(), current_timestamp, false),
    ('Spelling', uuid_generate_v4(), current_timestamp, false),
    ('History', uuid_generate_v4(), current_timestamp, false),
    ('Geography', uuid_generate_v4(), current_timestamp, false),
    ('Economics', uuid_generate_v4(), current_timestamp, false),
    ('World History', uuid_generate_v4(), current_timestamp, false),
    ('Political Science', uuid_generate_v4(), current_timestamp, false),
    ('Business', uuid_generate_v4(), current_timestamp, false),
    ('Finance', uuid_generate_v4(), current_timestamp, false),
    ('Marketing', uuid_generate_v4(), current_timestamp, false),
    ('Management', uuid_generate_v4(), current_timestamp, false),
    ('Technology', uuid_generate_v4(), current_timestamp, false),
    ('Health', uuid_generate_v4(), current_timestamp, false),
    ('Education', uuid_generate_v4(), current_timestamp, false),
    ('Career', uuid_generate_v4(), current_timestamp, false),
    ('Certification', uuid_generate_v4(), current_timestamp, false),
    ('Skills', uuid_generate_v4(), current_timestamp, false),
    ('Programming', uuid_generate_v4(), current_timestamp, false),
    ('Coding', uuid_generate_v4(), current_timestamp, false),
    ('Cybersecurity', uuid_generate_v4(), current_timestamp, false),
    ('Networks', uuid_generate_v4(), current_timestamp, false),
    ('Databases', uuid_generate_v4(), current_timestamp, false),
    ('AI', uuid_generate_v4(), current_timestamp, false),
    ('Machine Learning', uuid_generate_v4(), current_timestamp, false),
    ('Software Development', uuid_generate_v4(), current_timestamp, false),
    ('IT', uuid_generate_v4(), current_timestamp, false),
    ('Anatomy', uuid_generate_v4(), current_timestamp, false),
    ('Physiology', uuid_generate_v4(), current_timestamp, false),
    ('Diseases', uuid_generate_v4(), current_timestamp, false),
    ('Treatments', uuid_generate_v4(), current_timestamp, false),
    ('Public Health', uuid_generate_v4(), current_timestamp, false),
    ('Nutrition', uuid_generate_v4(), current_timestamp, false),
    ('Nursing', uuid_generate_v4(), current_timestamp, false),
    ('Medical Terminology', uuid_generate_v4(), current_timestamp, false),
    ('Pharmacology', uuid_generate_v4(), current_timestamp, false),
    ('Teaching Strategies', uuid_generate_v4(), current_timestamp, false),
    ('Pedagogy', uuid_generate_v4(), current_timestamp, false),
    ('Vocabulary', uuid_generate_v4(), current_timestamp, false),
    ('Phrases', uuid_generate_v4(), current_timestamp, false),
    ('Conversations', uuid_generate_v4(), current_timestamp, false),
    ('Translation', uuid_generate_v4(), current_timestamp, false),
    ('TOEFL', uuid_generate_v4(), current_timestamp, false),
    ('IELTS', uuid_generate_v4(), current_timestamp, false),
    ('Arts', uuid_generate_v4(), current_timestamp, false),
    ('Crafts', uuid_generate_v4(), current_timestamp, false),
    ('Music', uuid_generate_v4(), current_timestamp, false),
    ('Sports', uuid_generate_v4(), current_timestamp, false),
    ('Fitness', uuid_generate_v4(), current_timestamp, false),
    ('Travel', uuid_generate_v4(), current_timestamp, false),
    ('Cooking', uuid_generate_v4(), current_timestamp, false),
    ('Photography', uuid_generate_v4(), current_timestamp, false),
    ('Gardening', uuid_generate_v4(), current_timestamp, false),
    ('Movies', uuid_generate_v4(), current_timestamp, false),
    ('TV Shows', uuid_generate_v4(), current_timestamp, false),
    ('Books', uuid_generate_v4(), current_timestamp, false),
    ('Music', uuid_generate_v4(), current_timestamp, false),
    ('Celebrities', uuid_generate_v4(), current_timestamp, false),
    ('Games', uuid_generate_v4(), current_timestamp, false),
    ('Puzzles', uuid_generate_v4(), current_timestamp, false),
    ('Time Management', uuid_generate_v4(), current_timestamp, false),
    ('Productivity', uuid_generate_v4(), current_timestamp, false),
    ('Career Development', uuid_generate_v4(), current_timestamp, false),
    ('Parenting', uuid_generate_v4(), current_timestamp, false),
    ('General Knowledge', uuid_generate_v4(), current_timestamp, false),
    ('Fun Facts', uuid_generate_v4(), current_timestamp, false),
    ('Logic Puzzles', uuid_generate_v4(), current_timestamp, false),
    ('Flashcards', uuid_generate_v4(), current_timestamp, false);
