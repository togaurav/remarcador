-- Table: bitacoras

-- DROP TABLE bitacoras;

CREATE TABLE bitacoras
(
  id integer NOT NULL DEFAULT nextval('seq_id_bitacora'::regclass),
  id_remarcador integer,
  id_usuario integer,
  fecha_inicial integer,
  fecha_final integer,
  CONSTRAINT bitacoras_pkey PRIMARY KEY (id)
);

-- Table: centros_costos

-- DROP TABLE centros_costos;

CREATE TABLE centros_costos
(
  id_centro_costo smallint NOT NULL,
  nombre_centro_costo character varying(50),
  CONSTRAINT centros_costos_pkey PRIMARY KEY (id_centro_costo)
);

-- Table: cuentas

-- DROP TABLE cuentas;

CREATE TABLE cuentas
(
  id_cuenta smallint NOT NULL,
  nombre_cuenta character varying(50),
  CONSTRAINT cuentas_pkey PRIMARY KEY (id_cuenta)
);

-- Table: logs_accesos

-- DROP TABLE logs_accesos;

CREATE TABLE logs_accesos
(
  id integer NOT NULL DEFAULT nextval('seq_id_log_acceso'::regclass),
  id_usuario integer,
  fecha_acceso integer,
  hora_acceso integer,
  CONSTRAINT logs_accesos_pkey PRIMARY KEY (id)
);

-- Table: perfiles

-- DROP TABLE perfiles;

CREATE TABLE perfiles
(
  id smallint NOT NULL,
  nombre character varying(50),
  descripcion character varying(250),
  codigo character varying(250),
  CONSTRAINT perfiles_pkey PRIMARY KEY (id)
);

-- Table: remarcadores

-- DROP TABLE remarcadores;

CREATE TABLE remarcadores
(
  id integer NOT NULL,
  nombre character varying(50),
  local_remarcador character varying(50),
  marca_remarcador character varying(50),
  modelo_remarcador character varying(50),
  multiplicador real,
  tablero character varying(50),
  numero_medidor character varying(50),
  nodo character varying(50),
  observacion character varying(2000),
  id_centro_costo smallint,
  id_cuenta smallint,
  CONSTRAINT remarcadores_pkey PRIMARY KEY (id)
);

-- Table: usuarios

-- DROP TABLE usuarios;

CREATE TABLE usuarios
(
  id smallint NOT NULL,
  usuario character varying(50),
  clave character varying(50),
  nombre character varying(50),
  apellido_paterno character varying(50),
  apellido_materno character varying(50),
  email character varying(50),
  id_perfil smallint,
  CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);

-- Table: variables

-- DROP TABLE variables;

CREATE TABLE variables
(
  id integer NOT NULL,
  codigo_modem integer,
  id_canal integer,
  basededatos character varying(50),
  usuario_basededatos character varying(50),
  clave_basededatos character varying(50),
  tabla character varying(50),
  columna_id_equipo character varying(50),
  columna_id_variable_equipo character varying(50),
  columna_dato character varying(50),
  columna_fecha character varying(50),
  tipo_dato character varying(30),
  id_remarcador integer,
  CONSTRAINT variables_pkey PRIMARY KEY (id)
);




