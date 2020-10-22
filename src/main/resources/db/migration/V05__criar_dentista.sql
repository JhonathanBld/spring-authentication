CREATE TABLE dentista (
	ID BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    pessoa BIGINT(20),
    clinica BIGINT(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	

ALTER TABLE dentista ADD CONSTRAINT fk_dentista_pessoa FOREIGN KEY (pessoa) REFERENCES pessoa(id);
ALTER TABLE dentista ADD CONSTRAINT fk_dentista_clinica FOREIGN KEY (clinica) REFERENCES clinica(id);