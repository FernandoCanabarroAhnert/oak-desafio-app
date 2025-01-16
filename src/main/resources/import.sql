INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');
INSERT INTO imagem_produto (imagem_original,imagem_miniatura) VALUES ('','');

INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Camiseta Básica', 'Camiseta de algodão 100% disponível em várias cores', 49.90, true,1);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Calça Jeans', 'Calça jeans skinny de alta qualidade', 129.90, true,2);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Notebook Dell', 'Notebook Dell Inspiron com 16GB de RAM e 512GB SSD', 4999.00, true,3);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Smartphone Samsung', 'Galaxy S21 com 128GB de armazenamento', 2999.00, true,4);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Mouse Gamer', 'Mouse com iluminação RGB e DPI ajustável', 89.90, true,5);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Tênis Esportivo', 'Tênis para corrida com amortecimento reforçado', 249.90, true,6);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Mochila Executiva', 'Mochila de couro sintético com várias divisórias', 199.90, true,7);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Relógio Smartwatch', 'Smartwatch com monitoramento de batimentos cardíacos', 599.90, true,8);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Livro de Ficção', 'Livro de ficção científica premiado internacionalmente', 39.90, true,9);
INSERT INTO produtos (nome, descricao, valor, disponivel,imagem_id) VALUES ('Fone de Ouvido Bluetooth', 'Fones de ouvido sem fio com cancelamento de ruído', 349.90, true,10);

INSERT INTO roles (authority) VALUES ('ROLE_USER');
INSERT INTO roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO users (full_name,email,password,activated) VALUES ('Fernando','fernando@gmail.com','$2a$10$.rmvIkTkEgYq7SIKLQJSZ..Hsug4zFOvAuYkZbV1re0yY196rMlVq',TRUE);

INSERT INTO user_role (user_id,role_id) VALUES (1,1);
INSERT INTO user_role (user_id,role_id) VALUES (1,2);