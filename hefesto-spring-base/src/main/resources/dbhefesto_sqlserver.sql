drop sequence adm_parameter_seq;
drop sequence adm_parameter_category_seq;
drop sequence adm_page_profile_seq;
drop sequence adm_user_profile_seq;
drop sequence adm_profile_seq;
drop sequence adm_menu_seq;
drop sequence adm_page_seq;
drop sequence adm_user_seq;


drop table adm_parameter;
drop table adm_parameter_category;
drop table adm_page_profile;
drop table adm_user_profile;
drop table adm_profile;
drop table adm_menu;
drop table adm_page;
drop table adm_user;


CREATE SEQUENCE adm_menu_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;

CREATE SEQUENCE adm_page_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;
    
CREATE SEQUENCE adm_parameter_category_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;

CREATE SEQUENCE adm_parameter_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;

CREATE SEQUENCE adm_profile_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;

CREATE SEQUENCE adm_user_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;

CREATE SEQUENCE adm_page_profile_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;

CREATE SEQUENCE adm_user_profile_seq
as [bigint]
start with 1
increment by 1
minvalue 1
maxvalue 9223372036854775807
cycle
cache;
 
    
create table adm_page (
	pag_seq bigint not null DEFAULT (NEXT VALUE FOR adm_page_seq),
	pag_description nvarchar(255) not null,
	pag_url nvarchar(255) not null,
	constraint adm_page_pkey primary key (pag_seq),
	constraint adm_page_description_uk unique (pag_description),
	constraint adm_page_url_uk unique (pag_url)
);

create table adm_menu (
	mnu_seq bigint not null DEFAULT (NEXT VALUE FOR adm_menu_seq),
	mnu_description nvarchar(255) not null,
	mnu_parent_seq bigint,
	mnu_pag_seq bigint,
	mnu_order integer,
	constraint adm_menu_pkey primary key (mnu_seq),
	constraint adm_menu_uk unique (mnu_description),
	constraint adm_menu_parent_fkey foreign key (mnu_parent_seq) references adm_menu(mnu_seq),
	constraint adm_menu_page_fkey foreign key (mnu_pag_seq) references adm_page(pag_seq)
);
    
create table adm_parameter_category (
	pmc_seq bigint not null DEFAULT (NEXT VALUE FOR adm_parameter_category_seq),
	pmc_description nvarchar(64) not null,
	pmc_order bigint,
	constraint adm_parameter_category_pkey primary key (pmc_seq),
	constraint adm_pmc_uk unique (pmc_description)
);  
    
create table adm_parameter (
	par_seq bigint not null DEFAULT (NEXT VALUE FOR adm_parameter_seq),
	par_code nvarchar(64) not null,
	par_description nvarchar(255) not null,
	par_pmc_seq bigint not null,
	par_value nvarchar(4000),
	constraint adm_parameter_pkey primary key (par_seq),
	constraint adm_parameter_uk unique (par_description),
	constraint adm_parameter_pmc_fkey foreign key (par_pmc_seq) references adm_parameter_category(pmc_seq)
);
    
create table adm_profile (
	prf_seq bigint not null DEFAULT (NEXT VALUE FOR adm_profile_seq),
	prf_administrator char(1) NULL DEFAULT 'N',
	prf_description nvarchar(255) not null,
	prf_general char(1) NULL DEFAULT 'N',
	constraint adm_profile_pkey primary key (prf_seq),
	constraint adm_profile_uk unique (prf_description)
);

create table adm_user (
	usu_seq bigint not null DEFAULT (NEXT VALUE FOR adm_user_seq),
	usu_active char(1) NULL DEFAULT 'N',
	usu_email nvarchar(255),
	usu_login nvarchar(64) not null,
	usu_name nvarchar(64),
	usu_password nvarchar(128) not null,
	constraint adm_user_pkey primary key (usu_seq),
	constraint adm_user_name_uk unique (usu_name),
	constraint adm_user_email_uk unique (usu_email),
	constraint adm_user_login_uk unique (usu_login),
	constraint adm_user_password_uk unique (usu_password)
);

create table adm_page_profile (
	pgl_seq bigint not null DEFAULT (NEXT VALUE FOR adm_page_profile_seq),
	pgl_prf_seq bigint not null,
	pgl_pag_seq bigint not null,
	constraint adm_page_profile_pkey primary key (pgl_seq),
	constraint adm_page_profile_uk unique (pgl_pag_seq, pgl_prf_seq),
	constraint adm_pgl_page_fkey foreign key (pgl_pag_seq) references adm_page(pag_seq),
	constraint adm_pgl_profile_fkey foreign key (pgl_prf_seq) references adm_profile(prf_seq)
);
    
create table adm_user_profile (
	usp_seq bigint not null DEFAULT (NEXT VALUE FOR adm_user_profile_seq),
	usp_prf_seq bigint not null,
	usp_use_seq bigint not null,
	constraint adm_user_profile_pkey primary key (usp_seq),
	constraint adm_user_profile_uk unique (usp_prf_seq, usp_use_seq),
	constraint adm_usp_page_fkey foreign key (usp_prf_seq) references adm_profile(prf_seq),
	constraint adm_usp_profile_fkey foreign key (usp_use_seq) references adm_user(usu_seq)
);

