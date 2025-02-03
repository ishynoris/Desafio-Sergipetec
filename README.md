O projeto implementado usando:
- Java 12 
- Spring Boot
- Maven
- MySQL 8

Nacessário executar o script abaixo para criar usuário, banco de dados e tabelas principais;

A interface gráfica foi feita usando apenas HTML, JavaScript e Bootstrap. Não foi utilizado nenhum framework frontend.

Para acessar a interface gráfica, basta abrir o arquivo `frontend/index.html` em qualquer navegador

## SQL
Criar usuário e base banco de dados MySQL
```sql
CREATE DATABASE desafio_sergipetec DEFAULT CHARACTER SET = 'utf8mb4';

DROP USER IF EXISTS sergipetec;
CREATE USER IF NOT EXISTS 'sergipetec'@'localhost' IDENTIFIED BY 'sergipetec';
GRANT ALL ON desafio_sergipetec.* TO sergipetec@localhost;  
```

Criar tabelas
```sql
USE desafio_sergipetec;

CREATE TABLE fbe_fabricante (
	fbe_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	fbe_nome Varchar(100) NOT NULL
);

CREATE TABLE mdo_modelo (
	mdo_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	mdo_nome VARCHAR(100) NOT NULL,
	fbe_id INT NOT NULL,
	CONSTRAINT fk_mdo_fbe FOREIGN KEY (fbe_id) REFERENCES fbe_fabricante (fbe_id)
);

CREATE TABLE vco_veiculo (
	vco_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	fbe_id INT NOT NULL,
	mdo_id INT NOT NULL,
	vco_ano INT(4) NOT NULL,
	vco_preco FLOAT(9.2) NOT NULL,
	vco_tipo TINYINT(1) NOT NULL COMMENT 'VeiculoEnum',
	vco_portas TINYINT(1), 
	vco_combustivel TINYINT(1) COMMENT 'TipoCombustivelEnum',
	vco_cilindradas INT,
	vco_deletado TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0 = Não, 1 = Sim',

	CONSTRAINT fk_vco_fbe FOREIGN KEY (fbe_id) REFERENCES fbe_fabricante(fbe_id),
	CONSTRAINT fk_vco_mdo FOREIGN KEY (mdo_id) REFERENCES mdo_modelo(mdo_id)
);

```
Carga com dados iniciais
```sql
INSERT INTO fbe_fabricante(fbe_nome)
VALUES ('Volkswagen'), ('Ford'), ('Honda'), ('Peugeot'), ('Yamaha'), ('Fiat'), ('BYD'), ('Tesla'), ('Harley-Davidson');

INSERT INTO mdo_modelo(fbe_id, mdo_nome)
VALUES (1, 'Gol'), (1, 'Golf'), (1, 'Jetta'), (1, 'Amarok'), (1, 'Polo')
	, (2, 'Focus'), (2, 'Fiesta'), (2, 'Ranger'), (2, 'Mustang')
	, (3, 'Biz'), (3, 'CG 160 Titan'), (3, 'XR 190')
	, (4, '208'), (4, '308'), (4, '208 Active'), (4, '208 Style')
	, (5, 'NMAX ABS Connected'), (5, 'Fazer FZ15 Connected'), (5, 'Factor'), (5, 'Crosser Z ABS')
	, (6, 'Toro'), (6, 'Strada'), (6, 'Argo'), (6, 'Cronos'), (6, 'Mobi')
	, (7, 'Dolphin'), (7, 'King'), (7, 'Seal')
	, (8, 'Model S'), (8, 'Model Y'), (8, 'Model 3'), (8, 'Cybertruck')
	, (9, 'Sportster S'), (9, 'Street Glide Ultra'), (9, 'Street Bob'), (9, 'Low Rider S');


SELECT mdo.mdo_id, CONCAT(fbe.fbe_nome, ': ', mdo.mdo_nome) as mdo_nome
FROM desafio_sergipetec.mdo_modelo mdo
JOIN desafio_sergipetec.fbe_fabricante fbe
ON fbe.fbe_id = mdo.fbe_id;
```