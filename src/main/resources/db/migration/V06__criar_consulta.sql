CREATE TABLE consulta (
	ID BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    	dentista BIGINT(20),
    	paciente BIGINT(20),
	dataConsulta DATE,
	valor FLOAT
		

) ENGINE=InnoDB DEFAULT CHARSET=utf8;	

ALTER TABLE consulta ADD CONSTRAINT fk_consulta_dentista FOREIGN KEY (dentista) REFERENCES dentista(id);
ALTER TABLE consulta ADD CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente) REFERENCES paciente(id);