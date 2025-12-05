# ProjectWizard

ProjectWizard is a web-based project management system built with **Spring Boot**, **Thymeleaf**, **MySQL**, and **Spring JDBC**.  
It allows teams to organize projects, subprojects, tasks, subtasks, and assign users to work items.

---

## 🚀 Features

### 📁 Projects & Subprojects
- Create, view, and manage projects
- Each project may contain multiple **subprojects**
- Subprojects are grouped under their parent project

### 📝 Tasks
- Create tasks within a subproject
- Edit task details:
    - Title
    - Description
    - Estimated time
    - Actual time
    - Status
- Assign a **user** to a task
- Remove user from a task
- Supports **parent–child tasks** (subtasks)

### 🧩 Subtasks
- Tasks can have nested subtasks
- Subtasks inherit project/subproject context
- Subtasks can also have user assignment

### 👤 Users
- Stored in the database
- Users can be assigned to tasks or subtasks
- A task may have **0 or 1** assignee

### 💾 Database Access Using JDBC Template
- Custom **DAOs**
- Custom **RowMappers**

### 🎨 Frontend (Thymeleaf)
- HTML pages rendered using Thymeleaf templates
- Forms for creating/editing tasks, subtasks, and projects
- Dynamic routes like:
    - `/task/{taskId}/{projectId}/edit`
    - `/projects/{projectId}/subprojects/{subprojectId}/tasks`

---

## 🛠 Tech Stack

| Layer | Technology |
|------|------------|
| Backend | Spring Boot |
| View Engine | Thymeleaf |
| Database | MySQL |
| Database Access | Spring JDBC Template |
| Build Tool | Maven |
| Language | Java 17 |

---

## 👥 Contributors

Thanks to everyone who contributed to ProjectWizard:

<p align="left">

<a href="https://github.com/nikholt1">
  <img src="https://avatars.githubusercontent.com/u/140625263?v=4" width="80" height="80" style="border-radius:50%; margin-right:10px;" />
</a>

<a href="https://github.com/Manden9753">
  <img src="https://avatars.githubusercontent.com/u/198900159?v=4" width="80" height="80" style="border-radius:50%; margin-right:10px;" />
</a>

<a href="https://github.com/EllaCostello">
  <img src="https://avatars.githubusercontent.com/u/120627346?v=4" width="80" height="80" style="border-radius:50%; margin-right:10px;" />
</a>

<a href="https://github.com/iotuz">
  <img src="https://avatars.githubusercontent.com/u/158043925?v=4" width="80" height="80" style="border-radius:50%; margin-right:10px;" />
</a>

</p>
