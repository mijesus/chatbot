-- Criação do banco
CREATE DATABASE IF NOT EXISTS orioai;
USE orioai;

-- Tabela de Pacientes
CREATE TABLE paciente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    nascimento DATE NOT NULL
);

-- Tabela de Endereços
CREATE TABLE endereco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE
);

-- Tabela de Planos de Saúde
CREATE TABLE plano_saude (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    numero_cartao VARCHAR(50) NOT NULL,
    validade DATE NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE
);

-- Tabela de Histórico Médico
CREATE TABLE historico_medico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    diagnostico TEXT,
    tratamentos TEXT,
    exames_realizados TEXT,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE
);

-- Tabela de Triagem
CREATE TABLE triagem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sintomas_relato TEXT NOT NULL,
    nivel_prioridade VARCHAR(10) NOT NULL,
    mensagem_id INT
);

-- Tabela de Especialidades
CREATE TABLE especialidade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Tabela de Agendamentos
CREATE TABLE agendamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    data_consulta DATE NOT NULL,
    hora_consulta TIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE
);
