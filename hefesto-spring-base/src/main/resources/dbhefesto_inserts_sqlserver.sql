INSERT INTO adm_parameter_category (pmc_description,pmc_order) VALUES
	 ('Court Parameters',1),
	 ('Login Parameters',2),
	 ('E-mail Parameters',3),
	 ('Network connection Parameters',4),
	 ('System Parameters',NULL);
	 
INSERT INTO adm_parameter (par_code,par_description,par_pmc_seq,par_value) VALUES
	 ('NOME_TRIBUNAL','Nome do tribunal onde o sistema está instalado.',1,'Tribunal Regional do Trabalho da 1a. Região'),
	 ('SIGLA_TRIBUNAL','Sigla do tribunal onde o sistema está instalado.',1,'TRT1'),
	 ('CODIGO_UNIDADE_GESTORA','Código númérico de 6 dígitos que identifica o órgão no SIAFI.',1,'080009'),
	 ('APELIDO_UNIDADE_GESTORA','Código númérico de 3 dígitos da UG no código de barras da GRU.',1,'102'),
	 ('BLOQUEAR_LOGIN','Bloquear o sistema para que os usuários, exceto do administador, não façam login',2,'false'),
	 ('ATRIBUTO_LOGIN','Define o atributo usado para efetuar login no sistema. Este parâmetro pode ser preenchido com: NOME_USUARIO ou CPF',2,'NOME_USUARIO'),
	 ('SMTP_SERVIDOR','Servidor SMTP para que o sistema envie e-mail.',3,'smtp.trt1.jus.br'),
	 ('SMTP_PORTA','Porta do servidor SMTP para que o sistema envie e-mail.',3,'25'),
	 ('SMTP_USERNAME','Usuário para login no servidor SMTP.',3,NULL),
	 ('SMTP_PASSWORD','Senha para login no servidor SMTP.',3,NULL),
	 ('SMTP_EMAIL_FROM','E-mail do sistema.',3,'sistema@trt1.jus.br'),
	 ('PROXY_SERVIDOR','Servidor do Proxy.',4,'proxy.trtrio.gov.br'),
	 ('PROXY_PORTA','Porta do Proxy.',4,'8080'),
	 ('AMBIENTE_SISTEMA','Define o ambiente onde o sistema está instalado. Este parâmetro pode ser preenchido com: desenvolvimento, homologação ou produção',2,'Produção'),
	 ('MODO_TESTE','Habilitar o modo de teste por login, esquema do json [  { "ativo": "false", "login" : "fulano", "setor" : "DISAD", "cargo": "15426" } ]',2,'[ { "ativo": "false", "login" : "rafael.remiro", "setor" : "ESACS RJ", "cargo": "15426", "loginVirtual": "" }, { "ativo": "false", "login" : "fabricio.peres", "setor" : "CSEG", "cargo": "15426", "loginVirtual": "" }, { "ativo": "false", "login" : "henrique.souza", "setor" : "SAM", "cargo": "15426", "loginVirtual": "" }]');
	 
INSERT INTO adm_user (usu_active,usu_email,usu_login,usu_name,usu_password) VALUES
	 ('S','nao_responda@gmail.com','admin','Henrique','$2a$10$nhU38YCtaWpLzTIeG/uAIeGnu7GItrvGsQAQrgsjM9hN19cGp25N6');

INSERT INTO adm_page (pag_description,pag_url) VALUES
	 ('Category of Configuration Parameters','/admin/admParameterCategory'),
	 ('Edit Category of Configuration Parameters','/admin/admParameterCategoryEdit'),
	 ('Configuration Parameters','/admin/admParameter'),
	 ('Edit Configuration Parameters','/admin/admParameterEdit'),
	 ('Administer Profile','/admin/admProfile'),
	 ('Edit Administer Profile','/admin/admProfileEdit'),
	 ('Administer Page','/admin/admPage'),
	 ('Edit Administer Page','/admin/admPageEdit'),
	 ('Administer Menu','/admin/admMenu'),
	 ('Administer User','/admin/admUser'),
	 ('Edit Administer User','/admin/admUserEdit'),
	 ('Change Password','/admin/changePasswordEdit');

INSERT INTO adm_menu (mnu_description,mnu_parent_seq,mnu_pag_seq,mnu_order) VALUES
	 ('Administrative',NULL,NULL,1),
	 ('Category of Configuration Parameters',1,1,2),
	 ('Configuration Parameters',1,3,3),
	 ('Administer Profile',1,5,4),
	 ('Administer Page',1,7,5),
	 ('Administer Menu',1,9,6),
	 ('Administer User',1,10,7),
	 ('Change Password', 1, 12, 8);
	 
	 
INSERT INTO adm_profile (prf_administrator,prf_description,prf_general) VALUES
	 ('S','ADMIN','N'),
	 ('N','USER','S');
	 
INSERT INTO adm_user_profile (usp_prf_seq,usp_use_seq) VALUES
	 (1,1);
	 
INSERT INTO adm_page_profile (pgl_prf_seq,pgl_pag_seq) VALUES
	 (1,1),
	 (1,2),
	 (1,3),
	 (1,4),
	 (1,5),
	 (1,6),
	 (1,7),
	 (1,8),
	 (1,9),
	 (1,10),
	 (1,11),
	 (1,12);
	 
