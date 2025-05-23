Spotfei
Spotfei é um aplicativo em Java de música com integração ao PostgreSQL.
Ele servirá para mostrar dados relacionados à música armazenados em um banco de dados através de uma GUI.

O que será usado?
Swing (Java) + JDBC PostgreSQL + MVC (Model, View, Controller)

Funcionalidades do Spotfei:
• Cadastrar novo usuário
• Login de usuário
• Buscar músicas por nome, artista ou gênero
• Listar informações de músicas buscadas
• Curtir e descurtir músicas

• Gerenciar playlists:
• Criar, editar, excluir playlists
• Adicionar/remover músicas de playlists

• Visualizar histórico:
• Visualizar últimas 10 músicas buscadas
• Visualizar lista de músicas curtidas
• Visualizar lista de músicas descurtidas

Necessidades:
Ter um banco de dados seguindo a especificação do "Como usar"

Como usar:
Será necessário um banco de dados PostgreSQL local, com porta 5432, usuário "postgres" e senha "postegres" (atenção: a senha não é a senha 'postgres' padrão).
Usar os arquivos de texto localizados em Spotfei/DB/QueryCriarBancoDeDados.txt para gerar a estrutura de dados.
Usar os arquivos de texto localizados em Spotfei/DB/DadosQuery.txt para gerar dados sobre músicas e artistas.
Caso queira criar dados de músicas (coisa que o aplicativo não faz) e/ou relações de artistas com álbuns e músicas, sugiro usar o arquivo de texto em Spotfei/DB/DadosQuery.txt como base e substituir os valores pelos desejados.

Capacidades das telas:
| Login |
Front:
No canto superior direito existe uma label chamada "status". Ela serve para demonstrar se o usuário e senha não correspondem às informações do banco de dados.

Back:
Verifica se existe correspondência entre senha e usuário no banco de dados.
Carrega e passa os dados do usuário carregado para outras telas.

| Register |
Front:
No canto superior direito existe uma label chamada "status". Ela serve para demonstrar erros como:

campos de texto vazios

senha e confirmar senha não correspondem

idade igual ou menor que 0

se o usuário foi inserido no banco de dados ao clicar em "cadastrar"

Back:
Carrega dados inseridos nos campos de texto, gera dados usando eles e insere no banco de dados.

| Spotfei/home |
Front:
No canto superior esquerdo você tem a barra de pesquisa. É possível usar ela colocando nome de artista, música ou gênero da música, que irão aparecer resultados relacionados ao clicar em "pesquisar" (caso esteja vazio ao clicar, ele irá mostrar todas as músicas no banco).
No canto inferior direito você tem o atalho para a tela de playlists.
No meio, você tem uma lista de todas as músicas com suas informações.
No canto inferior esquerdo tem uma caixa de número (ID da música) com dois botões: "like" e "deslike". Ao colocar o ID da música e pressionar algum deles, você dará ou trocará like ou deslike dependendo do botão pressionado no ID selecionado.
No canto inferior esquerdo também tem uma caixa de ID da música e nome da playlist. Ao preencher o campo playlist com texto:

se a playlist não existir, ela será criada;

se ela existir e seu usuário for dono dela, você pode adicionar músicas nela.

Caso o campo ID da música tenha um número de ID válido, ou seja, maior do que 0 e que a música exista, será colocada na playlist do campo "playlist" a música. Se seu usuário for o dono da playlist, você poderá adicionar músicas a ela clicando em "adicionar".

No canto inferior direito, tem uma caixa de texto que, ao passar o mouse em cima da barra de pesquisa, mostrará seu histórico de pesquisa e também o status das ações que seu usuário fez.

Back:
Adiciona álbuns, baseado em campos de texto.
Gera a relação de like.
Filtra e demonstra dados filtrados pelo campo de pesquisa.
E carrega todas essas informações direto para o banco de dados.

| Playlists |
Front:
Tem um campo de pesquisa por nome da playlist, que mostra todas as músicas dentro da playlist selecionada.
Um botão de retorno para home/Spotfei.
Uma tabela que mostra a quantidade de músicas em cada playlist, o ID do dono e seu usuário.
Campos de exclusão de playlists inteiras, que funcionam de forma igual a "adicionar playlist", mas ele só exclui.
Campo de exclusão de música de uma playlist, que funciona de forma similar a "adicionar música na playlist", mas ele exclui no lugar de adicionar.
E tem o campo de texto para demonstrar o status das suas ações.

Back:
Remove álbuns.
Remove música de álbuns.
Filtra músicas por playlist.
E carrega todos os dados alterados para o banco.

Iniciação e instruções de uso:
Agora, com os dados para o aplicativo exibir, pode seguir para a inicialização do aplicativo: execute Spotfei/Spotfei/dist/Spotfei.jar.

A primeira tela que irá aparecer é o login. Procure no canto inferior um botão escrito "cadastre-se". Clique nele: isso irá te redirecionar para a tela Register.

Preencha os campos (o campo 'apelido' será o nome do seu usuário) e clique em "cadastrar".
Confirme se seu usuário foi inserido, olhando na label do canto superior direito. Depois, no botão no canto inferior direito, clique em "login": isso irá te redirecionar para a tela de login.
Insira seu usuário e senha correspondentes ao gerado na tela de cadastro e clique no botão "login". Agora você está na tela principal chamada Spotfei/home e consegue acessar todas as funcionalidades do aplicativo.

Para mais informações, veja "Capacidades das telas" ou entre em Spotfei/Spotfei/dist/JAVADOC e execute o HTML em um navegador.