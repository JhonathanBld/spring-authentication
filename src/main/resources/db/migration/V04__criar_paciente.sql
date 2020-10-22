CREATE TABLE paciente (
	ID BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    pessoa BIGINT(20),
    tratamento BIGINT(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	

ALTER TABLE paciente ADD CONSTRAINT fk_paciente_pessoa FOREIGN KEY (pessoa) REFERENCES pessoa(id);
ALTER TABLE paciente ADD CONSTRAINT fk_paciente_tratamento FOREIGN KEY (tratamento) REFERENCES tratamento(id);