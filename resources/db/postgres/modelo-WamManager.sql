-- Table: t_bigint

-- DROP TABLE t_bigint;

CREATE TABLE t_bigint
(
  codigo_modem bigint NOT NULL,
  id_canal integer NOT NULL,
  fecha_ts bigint NOT NULL,
  dato_bigint bigint NOT NULL,
  fecha_ts_serv bigint NOT NULL,
  CONSTRAINT pk_tbigint PRIMARY KEY (codigo_modem, id_canal, fecha_ts)
)
