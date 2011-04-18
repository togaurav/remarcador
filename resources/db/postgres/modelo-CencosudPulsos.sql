-- Table: "VARIABLES"

-- DROP TABLE "VARIABLES";

CREATE TABLE "VARIABLES"
(
  "ID" integer NOT NULL,
  "CODIGO_MODEM" integer,
  "ID_CANAL" integer,
  basededatos character varying(50),
  usuario_basededatos character varying(50),
  clave_basededatos character varying(50),
  tabla character varying(50),
  columna_id_equipo character varying(50),
  columna_id_variable_equipo character varying(50),
  columna_dato character varying(50),
  columna_fecha character varying(50),
  tipo_dato character varying(30),
  "ID_REMARCADOR" integer,
  CONSTRAINT variables_pkey PRIMARY KEY ("ID")
)

-- Table: "USUARIOS"

-- DROP TABLE "USUARIOS";

CREATE TABLE "USUARIOS"
(
  "ID" smallint NOT NULL,
  "USUARIO" character varying(50),
  "PASSWORD" character varying(50),
  "NOMBRE" character varying(50),
  "APELLIDO_PATERNO" character varying(50),
  "APELLIDO_MATERNO" character varying(50),
  "EMAIL" character varying(50),
  "ID_PERFIL" smallint,
  CONSTRAINT id_usuario PRIMARY KEY ("ID")
)

-- Table: "REMARCADORES"

-- DROP TABLE "REMARCADORES";

CREATE TABLE "REMARCADORES"
(
  "ID" integer NOT NULL,
  "NOMBRE" character varying(50),
  "LOCAL" character varying(50),
  "MARCA_REMARCADOR" character varying(50),
  "MODELO_REMARCADOR" character varying(50),
  "MULTIPLICADOR" real,
  "TABLERO" character varying(50),
  "NUMERO_MEDIDOR" character varying(50),
  "NODO" character varying(50),
  "OBSERVACION" character varying(2000),
  "ID_CENTRO_COSTO" smallint,
  "ID_CUENTA" smallint,
  CONSTRAINT remarcadores_pkey PRIMARY KEY ("ID")
)

-- Table: "PERFILES"

-- DROP TABLE "PERFILES";

CREATE TABLE "PERFILES"
(
  "ID" smallint NOT NULL,
  "NOMBRE" character varying(50),
  "DESCRIPCION" character varying(250),
  "CODIGO" character varying(250),
  CONSTRAINT id_perfil PRIMARY KEY ("ID")
)

-- Table: "CUENTAS"

-- DROP TABLE "CUENTAS";

CREATE TABLE "CUENTAS"
(
  "ID_CUENTA" smallint NOT NULL,
  "NOMBRE_CUENTA" character varying(50),
  CONSTRAINT "CUENTAS_pkey" PRIMARY KEY ("ID_CUENTA")
)

-- Table: "CENTROS_COSTOS"

-- DROP TABLE "CENTROS_COSTOS";

CREATE TABLE "CENTROS_COSTOS"
(
  "ID_CENTRO_COSTO" smallint NOT NULL,
  "NOMBRE_CENTRO_COSTO" character varying(50),
  CONSTRAINT "CENTROS_COSTOS_pkey" PRIMARY KEY ("ID_CENTRO_COSTO")
)