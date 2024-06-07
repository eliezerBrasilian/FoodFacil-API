FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para a imagem
COPY . .

# Copia o script de entrada para a imagem
COPY entrypoint.sh /entrypoint.sh

# Dá permissão de execução ao script de entrada
RUN chmod +x /entrypoint.sh

# Define o script de entrada como o ponto de entrada
ENTRYPOINT ["/entrypoint.sh"]
