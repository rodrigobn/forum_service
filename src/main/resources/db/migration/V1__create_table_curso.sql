create table curso(
    id bigint not null auto_increment,
    nome varchar(50) not null,
    categoria varchar(50) not null,
    primary key (id)
);

insert into curso (id, nome, categoria) values (1, 'Java', 'Programação');
insert into curso (id, nome, categoria) values (2, 'Python', 'Programação');
insert into curso (id, nome, categoria) values (3, 'JavaScript', 'Programação');
insert into curso (id, nome, categoria) values (4, 'HTML', 'Front-end');
insert into curso (id, nome, categoria) values (5, 'CSS', 'Front-end');
insert into curso (id, nome, categoria) values (6, 'React', 'Front-end');
insert into curso (id, nome, categoria) values (7, 'Angular', 'Front-end');
insert into curso (id, nome, categoria) values (8, 'Vue.js', 'Front-end');
insert into curso (id, nome, categoria) values (9, 'SQL', 'Banco de Dados');
insert into curso (id, nome, categoria) values (10, 'NoSQL', 'Banco de Dados');
insert into curso (id, nome, categoria) values (11, 'PostgreSQL', 'Banco de Dados');
insert into curso (id, nome, categoria) values (12, 'MongoDB', 'Banco de Dados');
insert into curso (id, nome, categoria) values (13, 'Spring Boot', 'Programação');
insert into curso (id, nome, categoria) values (14, 'Django', 'Programação');
insert into curso (id, nome, categoria) values (15, 'Flask', 'Programação');
insert into curso (id, nome, categoria) values (16, 'Kotlin', 'Programação');
insert into curso (id, nome, categoria) values (17, 'Swift', 'Programação');
insert into curso (id, nome, categoria) values (18, 'C#', 'Programação');
insert into curso (id, nome, categoria) values (19, 'PHP', 'Programação');
insert into curso (id, nome, categoria) values (20, 'Ruby', 'Programação');
insert into curso (id, nome, categoria) values (21, 'Go', 'Programação');
insert into curso (id, nome, categoria) values (22, 'Rust', 'Programação');
insert into curso (id, nome, categoria) values (23, 'TypeScript', 'Programação');
insert into curso (id, nome, categoria) values (24, 'Machine Learning', 'Inteligência Artificial');
insert into curso (id, nome, categoria) values (25, 'Deep Learning', 'Inteligência Artificial');
insert into curso (id, nome, categoria) values (26, 'Data Science', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (27, 'Big Data', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (28, 'Hadoop', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (29, 'Spark', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (30, 'DevOps', 'Infraestrutura');
insert into curso (id, nome, categoria) values (31, 'Docker', 'Infraestrutura');
insert into curso (id, nome, categoria) values (32, 'Kubernetes', 'Infraestrutura');
insert into curso (id, nome, categoria) values (33, 'AWS', 'Cloud');
insert into curso (id, nome, categoria) values (34, 'Azure', 'Cloud');
insert into curso (id, nome, categoria) values (35, 'Google Cloud', 'Cloud');
insert into curso (id, nome, categoria) values (36, 'Terraform', 'Cloud');
insert into curso (id, nome, categoria) values (37, 'Linux', 'Sistemas Operacionais');
insert into curso (id, nome, categoria) values (38, 'Windows Server', 'Sistemas Operacionais');
insert into curso (id, nome, categoria) values (39, 'Ethical Hacking', 'Segurança da Informação');
insert into curso (id, nome, categoria) values (40, 'Cybersecurity', 'Segurança da Informação');
insert into curso (id, nome, categoria) values (41, 'Blockchain', 'Segurança da Informação');
insert into curso (id, nome, categoria) values (42, 'R', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (43, 'Pandas', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (44, 'NumPy', 'Ciência de Dados');
insert into curso (id, nome, categoria) values (45, 'TensorFlow', 'Inteligência Artificial');
insert into curso (id, nome, categoria) values (46, 'PyTorch', 'Inteligência Artificial');
insert into curso (id, nome, categoria) values (47, 'Unity', 'Desenvolvimento de Jogos');
insert into curso (id, nome, categoria) values (48, 'Unreal Engine', 'Desenvolvimento de Jogos');
insert into curso (id, nome, categoria) values (49, 'MATLAB', 'Engenharia');
insert into curso (id, nome, categoria) values (50, 'Simulink', 'Engenharia');
insert into curso (id, nome, categoria) values (51, 'AutoCAD', 'Engenharia');
insert into curso (id, nome, categoria) values (52, 'SolidWorks', 'Engenharia');
insert into curso (id, nome, categoria) values (53, 'Catia', 'Engenharia');
insert into curso (id, nome, categoria) values (54, 'Ansys', 'Engenharia');
insert into curso (id, nome, categoria) values (55, 'Revit', 'Engenharia');

-- Ajuste do auto incremento para o próximo valor desejado
ALTER TABLE curso AUTO_INCREMENT = 56;