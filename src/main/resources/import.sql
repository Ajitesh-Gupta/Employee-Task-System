INSERT INTO departments (id, name, description, created_at) VALUES 
(1, 'Engineering', 'Software development and engineering', CURRENT_TIMESTAMP),
(2, 'Marketing', 'Marketing and brand management', CURRENT_TIMESTAMP),
(3, 'Human Resources', 'HR and employee management', CURRENT_TIMESTAMP),
(4, 'Sales', 'Sales and business development', CURRENT_TIMESTAMP),
(5, 'Finance', 'Financial planning and accounting', CURRENT_TIMESTAMP);

INSERT INTO employees (id, name, email, position, join_date, department_id, created_at) VALUES 
(1, 'John Smith', 'john.smith@company.com', 'Senior Developer', '2023-01-15', 1, CURRENT_TIMESTAMP),
(2, 'Sarah Johnson', 'sarah.johnson@company.com', 'Product Manager', '2023-02-20', 1, CURRENT_TIMESTAMP),
(3, 'Michael Brown', 'michael.brown@company.com', 'Marketing Manager', '2023-03-10', 2, CURRENT_TIMESTAMP),
(4, 'Emily Davis', 'emily.davis@company.com', 'HR Specialist', '2023-04-05', 3, CURRENT_TIMESTAMP),
(5, 'David Wilson', 'david.wilson@company.com', 'Sales Executive', '2023-05-12', 4, CURRENT_TIMESTAMP),
(6, 'Lisa Anderson', 'lisa.anderson@company.com', 'Financial Analyst', '2023-06-18', 5, CURRENT_TIMESTAMP),
(7, 'James Martinez', 'james.martinez@company.com', 'Junior Developer', '2023-07-22', 1, CURRENT_TIMESTAMP),
(8, 'Jennifer Taylor', 'jennifer.taylor@company.com', 'Designer', '2023-08-14', 2, CURRENT_TIMESTAMP),
(9, 'Robert Thomas', 'robert.thomas@company.com', 'DevOps Engineer', '2023-09-08', 1, CURRENT_TIMESTAMP),
(10, 'Maria Garcia', 'maria.garcia@company.com', 'Content Writer', '2023-10-01', 2, CURRENT_TIMESTAMP);

UPDATE departments SET head_id = 2 WHERE id = 1;
UPDATE departments SET head_id = 3 WHERE id = 2;
UPDATE departments SET head_id = 4 WHERE id = 3;
UPDATE departments SET head_id = 5 WHERE id = 4;
UPDATE departments SET head_id = 6 WHERE id = 5;

INSERT INTO tasks (id, title, description, status, priority, assigned_to, due_date, created_at, completed_at) VALUES 
-- Completed tasks
(1, 'Setup Development Environment', 'Install and configure all necessary development tools', 'DONE', 'HIGH', 1, TIMESTAMP '2025-10-15 09:00:00', TIMESTAMP '2025-10-01 08:00:00', TIMESTAMP '2025-10-10 16:30:00'),
(2, 'Database Schema Design', 'Design the initial database schema for the application', 'DONE', 'URGENT', 1, TIMESTAMP '2025-10-20 17:00:00', TIMESTAMP '2025-10-05 09:00:00', TIMESTAMP '2025-10-18 14:00:00'),
(3, 'Create Marketing Campaign', 'Develop Q4 marketing campaign strategy', 'DONE', 'HIGH', 3, TIMESTAMP '2025-10-25 18:00:00', TIMESTAMP '2025-10-01 10:00:00', TIMESTAMP '2025-10-20 17:00:00'),

-- In progress tasks
(4, 'Implement User Authentication', 'Add JWT-based authentication to the API', 'IN_PROGRESS', 'URGENT', 1, TIMESTAMP '2025-11-15 17:00:00', TIMESTAMP '2025-10-20 08:00:00', NULL),
(5, 'Design Landing Page', 'Create mockups for the new landing page', 'IN_PROGRESS', 'MEDIUM', 8, TIMESTAMP '2025-11-10 16:00:00', TIMESTAMP '2025-10-22 09:00:00', NULL),
(6, 'Update Employee Handbook', 'Review and update company policies', 'IN_PROGRESS', 'LOW', 4, TIMESTAMP '2025-11-20 17:00:00', TIMESTAMP '2025-10-15 10:00:00', NULL),

-- Todo tasks
(7, 'Write API Documentation', 'Document all REST endpoints with examples', 'TODO', 'MEDIUM', 2, TIMESTAMP '2025-11-25 17:00:00', TIMESTAMP '2025-10-25 08:00:00', NULL),
(8, 'Setup CI/CD Pipeline', 'Configure automated testing and deployment', 'TODO', 'HIGH', 9, TIMESTAMP '2025-11-18 17:00:00', TIMESTAMP '2025-10-26 09:00:00', NULL),
(9, 'Client Presentation Preparation', 'Prepare slides for quarterly review', 'TODO', 'URGENT', 5, TIMESTAMP '2025-11-05 14:00:00', TIMESTAMP '2025-10-28 10:00:00', NULL),
(10, 'Budget Analysis Report', 'Analyze Q3 expenses and prepare report', 'TODO', 'HIGH', 6, TIMESTAMP '2025-11-12 17:00:00', TIMESTAMP '2025-10-29 08:00:00', NULL),

-- Overdue tasks (due date in past, not completed)
(11, 'Code Review Legacy System', 'Review and document legacy codebase', 'TODO', 'MEDIUM', 7, TIMESTAMP '2025-10-28 17:00:00', TIMESTAMP '2025-10-10 09:00:00', NULL),
(12, 'Social Media Content Calendar', 'Plan social media posts for November', 'TODO', 'LOW', 10, TIMESTAMP '2025-10-30 17:00:00', TIMESTAMP '2025-10-15 10:00:00', NULL),

-- Cancelled tasks
(13, 'Migrate to Old Framework', 'Migration cancelled due to framework deprecation', 'CANCELLED', 'MEDIUM', 1, TIMESTAMP '2025-11-30 17:00:00', TIMESTAMP '2025-10-05 08:00:00', NULL),
(14, 'Outdated Feature Request', 'Feature request cancelled by product team', 'CANCELLED', 'LOW', 7, TIMESTAMP '2025-12-01 17:00:00', TIMESTAMP '2025-10-08 09:00:00', NULL),

-- More diverse tasks
(15, 'Performance Optimization', 'Optimize database queries and API response times', 'TODO', 'HIGH', 9, TIMESTAMP '2025-11-22 17:00:00', TIMESTAMP '2025-10-30 08:00:00', NULL),
(16, 'Employee Onboarding Training', 'Conduct training session for new hires', 'TODO', 'MEDIUM', 4, TIMESTAMP '2025-11-08 15:00:00', TIMESTAMP '2025-10-31 09:00:00', NULL),
(17, 'Security Audit', 'Perform comprehensive security audit of application', 'TODO', 'URGENT', 9, TIMESTAMP '2025-11-14 17:00:00', TIMESTAMP '2025-10-31 10:00:00', NULL),
(18, 'Sales Dashboard Feature', 'Implement new analytics dashboard for sales team', 'IN_PROGRESS', 'HIGH', 2, TIMESTAMP '2025-11-20 17:00:00', TIMESTAMP '2025-10-25 08:00:00', NULL),
(19, 'Customer Feedback Analysis', 'Analyze and report on customer feedback from Q3', 'TODO', 'MEDIUM', 3, TIMESTAMP '2025-11-16 17:00:00', TIMESTAMP '2025-10-28 09:00:00', NULL),
(20, 'Mobile App Prototype', 'Create initial prototype for mobile application', 'TODO', 'LOW', 8, TIMESTAMP '2025-12-01 17:00:00', TIMESTAMP '2025-10-30 10:00:00', NULL);

ALTER SEQUENCE departments_seq RESTART WITH 6;
ALTER SEQUENCE employees_seq RESTART WITH 11;
ALTER SEQUENCE tasks_seq RESTART WITH 21;
