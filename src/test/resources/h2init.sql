
DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS users_to_project;
DROP TABLE IF EXISTS sub_project;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       user_password VARCHAR(255) NOT NULL
);


CREATE TABLE project (
                         project_id INT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         project_description TEXT,
                         created_at DATE,
                         deadline DATE
);

CREATE TABLE sub_project (
                             sub_project_id INT AUTO_INCREMENT PRIMARY KEY,
                             parent_id INT NOT NULL,
                             subproject_title VARCHAR(255) NOT NULL,
                             sub_project_description TEXT,
                             created_at DATE,
                             deadline DATE,
                             FOREIGN KEY (parent_id) REFERENCES project(project_id)
                                 ON DELETE CASCADE
);

CREATE TABLE users_to_project (
                                  user_id INT NOT NULL,
                                  project_id INT NOT NULL,
                                  PRIMARY KEY (user_id, project_id),

                                  FOREIGN KEY (user_id) REFERENCES users(user_id)
                                      ON DELETE CASCADE,

                                  FOREIGN KEY (project_id) REFERENCES project(project_id)
                                      ON DELETE CASCADE
);

CREATE TABLE task (
                      task_id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      task_description TEXT,
                      parent_id INT not null,
                      task_status ENUM('NoStatus','InProgress','InReview','Done') DEFAULT 'NoStatus',
                      estimated_time DOUBLE,
                      assignee_id INT,
                      actual_time DOUBLE,


                      FOREIGN KEY (parent_id) REFERENCES sub_project(sub_project_id)
                          ON DELETE CASCADE,

                      FOREIGN KEY (assignee_id) REFERENCES users(user_id)
                          ON DELETE SET NULL
);

CREATE TABLE subtask (
                         subtask_id INT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         subtask_description TEXT,
                         subtask_status ENUM('NoStatus','InProgress','InReview','Done') DEFAULT 'NoStatus',
                         parent_id INT NOT NULL,
                         estimated_time DOUBLE,
                         assignee_id INT,
                         actual_time DOUBLE,

                         FOREIGN KEY (parent_id) REFERENCES task(task_id)
                             ON DELETE CASCADE,


                         FOREIGN KEY (assignee_id) REFERENCES users(user_id)
                             ON DELETE SET NULL
);

INSERT INTO users(email, username, user_password)
VALUES ('adminEmail@email.com', 'admin', 'admin');

INSERT INTO project (title, project_description, created_at, deadline)
VALUES ('projectTestTitle', 'projectTestDescription','2024-01-01', '2025-01-01');

INSERT INTO sub_project(parent_id, subproject_title, sub_project_description, created_at, deadline)
VALUES (1, 'subprojectTestTitle', 'subprojectTestDescription', '2024-01-01', '2025-01-01');

INSERT INTO users_to_project(user_id, project_id)
VALUES (1, 1);

INSERT INTO task (title, task_description, parent_id,task_status, estimated_time, assignee_id, actual_time)
VALUES ('taskTestTitle', 'taskTestDescription', 1, 'NoStatus', 2.5, 1, 2.5);

INSERT INTO subtask(title, subtask_description, subtask_status, parent_id, estimated_time, assignee_id, actual_time)
VALUES ('subtaskTestTitle', 'subtaskTestDescription',  'NoStatus', 1, 2.5, 1, 2.5);


