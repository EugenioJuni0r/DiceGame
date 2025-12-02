-- ====================================================================
-- SCHEMA SQL - DICE GAME (PostgreSQL)
-- ====================================================================
-- Data: 01/12/2025
-- Sistema de Apostas - Rolando os Dados
-- ====================================================================

-- ====================================================================
-- TABELA: users
-- Descrição: Armazena dados dos usuários do sistema
-- ====================================================================
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ====================================================================
-- TABELA: bet
-- Descrição: Armazena as apostas do sistema
-- ====================================================================
CREATE TABLE IF NOT EXISTS bet (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  user_id BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- ====================================================================
-- TABELA: gamble
-- Descrição: Registra cada aposta realizada (palpite) vinculada a um usuário e uma bet
-- ====================================================================
CREATE TABLE IF NOT EXISTS gamble (
  id BIGSERIAL PRIMARY KEY,
  value NUMERIC(10,2) NOT NULL,
  user_guess INTEGER NOT NULL,
  user_id BIGINT,
  bet_id BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
  FOREIGN KEY (bet_id) REFERENCES bet(id) ON DELETE CASCADE
);

-- ====================================================================
-- ÍNDICES
-- ====================================================================
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_bet_user_id ON bet(user_id);
CREATE INDEX IF NOT EXISTS idx_gamble_user_id ON gamble(user_id);
CREATE INDEX IF NOT EXISTS idx_gamble_bet_id ON gamble(bet_id);

-- ====================================================================
-- DADOS INICIAIS - USUÁRIOS
-- ====================================================================
-- Usuário Admin padrão (senha: 123456 - HASH bcrypt)
INSERT INTO users (name, email, password, role) 
VALUES (
  'Administrador',
  'admin@dicegame.com',
  '$2a$12$ipaKsWtoSzjGcZjF4lHKUOZkk4DbnEmV5/JtyxVj2MAbmp5n0YsYm',
  'ADMIN'
) ON CONFLICT (email) DO NOTHING;

-- Usuário comum padrão (senha: 123456 - HASH bcrypt)
INSERT INTO users (name, email, password, role) 
VALUES (
  'Usuário Teste',
  'user@dicegame.com',
  '$2a$12$ipaKsWtoSzjGcZjF4lHKUOZkk4DbnEmV5/JtyxVj2MAbmp5n0YsYm',
  'USER'
) ON CONFLICT (email) DO NOTHING;

-- Usuário Dealer padrão (senha: 123456 - HASH bcrypt)
INSERT INTO users (name, email, password, role) 
VALUES (
  'Dealer Principal',
  'dealer@dicegame.com',
  '$2a$12$ipaKsWtoSzjGcZjF4lHKUOZkk4DbnEmV5/JtyxVj2MAbmp5n0YsYm',
  'USER'
) ON CONFLICT (email) DO NOTHING;

-- ====================================================================
-- DADOS INICIAIS - APOSTAS
-- ====================================================================
-- Aposta de teste (relacionada ao admin)
INSERT INTO bet (name, user_id)
SELECT 'Aposta de Teste 1', id FROM users WHERE email = 'admin@dicegame.com'
ON CONFLICT DO NOTHING;

-- Aposta de teste 2 (relacionada ao dealer)
INSERT INTO bet (name, user_id)
SELECT 'Aposta de Teste 2', id FROM users WHERE email = 'dealer@dicegame.com'
ON CONFLICT DO NOTHING;

-- ====================================================================
-- DADOS INICIAIS - GAMBLE
-- ====================================================================
-- Exemplo de gambles relacionados às bets criadas acima
INSERT INTO gamble (value, user_guess, user_id, bet_id)
SELECT 10.00, 4, u.id, b.id
FROM users u
JOIN bet b ON b.name = 'Aposta de Teste 1'
WHERE u.email = 'user@dicegame.com'
ON CONFLICT DO NOTHING;

INSERT INTO gamble (value, user_guess, user_id, bet_id)
SELECT 5.50, 2, u.id, b.id
FROM users u
JOIN bet b ON b.name = 'Aposta de Teste 2'
WHERE u.email = 'user@dicegame.com'
ON CONFLICT DO NOTHING;

-- ====================================================================
-- SCRIPTS DE LIMPEZA (COMENTADO - USE COM CUIDADO!)
-- ====================================================================
-- Para limpar todos os dados e recriar as tabelas:
-- DROP TABLE IF EXISTS bet CASCADE;
-- DROP TABLE IF EXISTS users CASCADE;
-- (Depois execute os comandos CREATE TABLE acima novamente)

-- ====================================================================
-- VERIFICAÇÕES
-- ====================================================================
-- Listar todas as tabelas:
-- SELECT * FROM information_schema.tables WHERE table_schema = 'public';

-- Contar usuários:
-- SELECT COUNT(*) as total_usuarios FROM users;

-- Contar apostas:
-- SELECT COUNT(*) as total_apostas FROM bet;

-- Listar usuários e suas apostas:
-- SELECT u.id, u.name, u.email, u.role, COUNT(b.id) as total_apostas
-- FROM users u
-- LEFT JOIN bet b ON u.id = b.user_id
-- GROUP BY u.id, u.name, u.email, u.role;
