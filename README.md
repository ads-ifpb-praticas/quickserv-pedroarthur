# Quickserv

Projeto final da disciplinas Práticas de Programação.

## Descrição

O Quickserv trata-se de um sistema web que auxilia as pessoas a encontrar profissionais qualificados de acordo com o serviço procurado. 

## Requisitos

- **RF01** O sistema deve prover uma maneira de um cliente se cadastrar, alterar seus dados e desativar sua conta quando necessário.
- **RF02** O sistema deve prover uma maneira de um profissional enviar uma solicitação de cadastro, alterar seus dados e desativar sua conta quando necessário.
- **RF03** Um cadastro de um administrador padrão deve ser realizado automaticamente caso não haja administradores cadastrados.
- **RF04** Clientes, Profissionais e Administradores devem acessor o sistema através de uma autenticação baseada em e-mail e senha.
- **RF05** Todo cliente pode registrar a solicitação de um registro informando dados como a descrição, um endereço superficial, o tipo do serviço procurado e sugestão de horários.
- **RF06** Com base em uma solicitação de serviço feita por um cliente, todo profissional poderá registrar uma proposta para o mesmo especificando uma descrição, um valor em R$, e as datas em que o mesmo poderá atender ao cliente.
- **RF07** Um administrador pode aprovar ou negar a solicitação de cadastro de um profissional. O profissional só poderá acessar o sistema caso o administrador aprove a solicitação.
- **RF08** Um profissional poderá filtrar solicitações de serviços com base no tipo do serviço solicitado.
- **RF09** O cliente pode aceitar uma proposta feita por um profissional a uma de suas solicitações. Um serviço será registrado com base na solicitação e na proposta.
- **RF10** Quando o tempo proposto paro o serviço encerrar, o cliente poderá avaliar o serviço prestado pelo profissional.
- **RF11** Passado um dia do tempo proposto para o serviço, um e-mail deverá ser enviado ao cliente solicitando a avaliação do serviço prestado.
- **RF12** Cada Profissional deve ter uma agenda que poderá ser vista por qualquer cliente. A agenda mostrará os horários livres de cada profissional baseado nos serviços em que os mesmos se encontram.
- **RF13** Busca pelos melhores prestadores de serviço baseado nas avaliações dos serviços prestados pelos mesmos.