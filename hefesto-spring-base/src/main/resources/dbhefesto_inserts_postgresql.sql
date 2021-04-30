INSERT INTO public.adm_parameter_category (pmc_seq,pmc_description,pmc_order) VALUES
	 (1,'Court Parameters',1),
	 (2,'Login Parameters',2),
	 (3,'E-mail Parameters',3),
	 (4,'Network connection Parameters',4),
	 (5,'System Parameters',NULL);
	 
INSERT INTO public.adm_parameter (par_seq,par_code,par_description,par_pmc_seq,par_value) VALUES
	 (1,'NOME_TRIBUNAL','Nome do tribunal onde o sistema está instalado.',1,'Tribunal Regional do Trabalho da 1a. Região'),
	 (2,'SIGLA_TRIBUNAL','Sigla do tribunal onde o sistema está instalado.',1,'TRT1'),
	 (3,'CODIGO_UNIDADE_GESTORA','Código númérico de 6 dígitos que identifica o órgão no SIAFI.',1,'080009'),
	 (4,'APELIDO_UNIDADE_GESTORA','Código númérico de 3 dígitos da UG no código de barras da GRU.',1,'102'),
	 (5,'BLOQUEAR_LOGIN','Bloquear o sistema para que os usuários, exceto do administador, não façam login',2,'false'),
	 (6,'ATRIBUTO_LOGIN','Define o atributo usado para efetuar login no sistema. Este parâmetro pode ser preenchido com: NOME_USUARIO ou CPF',2,'NOME_USUARIO'),
	 (7,'SMTP_SERVIDOR','Servidor SMTP para que o sistema envie e-mail.',3,'smtp.trt1.jus.br'),
	 (8,'SMTP_PORTA','Porta do servidor SMTP para que o sistema envie e-mail.',3,'25'),
	 (9,'SMTP_USERNAME','Usuário para login no servidor SMTP.',3,NULL),
	 (10,'SMTP_PASSWORD','Senha para login no servidor SMTP.',3,NULL),
	 (11,'SMTP_EMAIL_FROM','E-mail do sistema.',3,'sistema@trt1.jus.br'),
	 (12,'PROXY_SERVIDOR','Servidor do Proxy.',4,'proxy.trtrio.gov.br'),
	 (13,'PROXY_PORTA','Porta do Proxy.',4,'8080'),
	 (14,'AMBIENTE_SISTEMA','Define o ambiente onde o sistema está instalado. Este parâmetro pode ser preenchido com: desenvolvimento, homologação ou produção',2,'Produção'),
	 (15,'MODO_TESTE','Habilitar o modo de teste por login, esquema do json [  { "ativo": "false", "login" : "fulano", "setor" : "DISAD", "cargo": "15426" } ]',2,'[ { "ativo": "false", "login" : "rafael.remiro", "setor" : "ESACS RJ", "cargo": "15426", "loginVirtual": "" }, { "ativo": "false", "login" : "fabricio.peres", "setor" : "CSEG", "cargo": "15426", "loginVirtual": "" }, { "ativo": "false", "login" : "henrique.souza", "setor" : "SAM", "cargo": "15426", "loginVirtual": "" }]');
	 
INSERT INTO public.adm_user (usu_seq,usu_active,usu_email,usu_login,usu_name,usu_password) VALUES
	 (1,'S','nao_responda@gmail.com','admin','Henrique','$2a$10$nhU38YCtaWpLzTIeG/uAIeGnu7GItrvGsQAQrgsjM9hN19cGp25N6');

INSERT INTO public.adm_page (pag_seq,pag_description,pag_url) VALUES
	 (1,'Category of Configuration Parameters','/admin/admParameterCategory'),
	 (2,'Edit Category of Configuration Parameters','/admin/admParameterCategoryEdit'),
	 (3,'Configuration Parameters','/admin/admParameter'),
	 (4,'Edit Configuration Parameters','/admin/admParameterEdit'),
	 (5,'Administer Profile','/admin/admProfile'),
	 (6,'Edit Administer Profile','/admin/admProfileEdit'),
	 (7,'Administer Page','/admin/admPage'),
	 (8,'Edit Administer Page','/admin/admPageEdit'),
	 (9,'Administer Menu','/admin/admMenu'),
	 (10,'Administer User','/admin/admUser'),
	 (11,'Edit Administer User','/admin/admUserEdit'),
	 (12,'Change Password','/admin/changePasswordEdit');

INSERT INTO public.adm_menu (mnu_seq,mnu_description,mnu_parent_seq,mnu_pag_seq,mnu_order) VALUES
	 (1,'Administrative',NULL,NULL,1),
	 (2,'Category of Configuration Parameters',1,1,2),
	 (3,'Configuration Parameters',1,3,3),
	 (4,'Administer Profile',1,5,4),
	 (5,'Administer Page',1,7,5),
	 (6,'Administer Menu',1,9,6),
	 (7,'Administer User',1,10,7),
	 (8, 'Change Password', 1, 12, 8);
	 
	 
INSERT INTO public.adm_profile (prf_seq,prf_administrator,prf_description,prf_general) VALUES
	 (1,'S','ADMIN','N'),
	 (2,'N','USER','S');
	 
INSERT INTO public.adm_user_profile (usp_seq,usp_prf_seq,usp_use_seq) VALUES
	 (1,1,1);
	 
INSERT INTO public.adm_page_profile (pgl_seq,pgl_prf_seq,pgl_pag_seq) VALUES
	 (1,1,1),
	 (2,1,2),
	 (3,1,3),
	 (4,1,4),
	 (5,1,5),
	 (6,1,6),
	 (7,1,7),
	 (8,1,8),
	 (9,1,9),
	 (10,1,10),
	 (11,1,11),
	 (12,1,12);
	 
