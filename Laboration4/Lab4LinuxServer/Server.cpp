#include "ServerLinux.h"

int maxSelect;
int serverSocket;
int numberOfClients;
GameGrid* grid;
MsgHead response;
bool endGame = false;

int main() 
{
	Server server;
	server.initiate(5300, 1);
}

std::vector<std::pair<int, bool>> playerStates;
std::vector<std::pair<int, int>> clientSockets;


void Server::initiate(int port, int maxClients) {
	int errorCheck = -1;
	serverSocket = socket(AF_INET, SOCK_STREAM, IPROTO_TCP);
	maxSelect = serverSocket;

	if (serverSocket == errorCheck) {
		perror("cannot create socket");
		exit(EXIT_FAILURE);
	}

	// Setting up the port
	sockaddr_in target;
	target.sin_family;
	target.sin_port = htons(port);
	target.sin_addr.s_addr = INADDR_ANY;

	// Bind the port
	bind(serverSocket, (sockaddr*)&target, sizeof(target));
	listen(serverSocket, SOMAXCONN);


	sockaddr_in  client;
	int clientSize = sizeof(client);

	numberOfClients = 0;

	//loop to find the maximum amount of players
	while (true) {
		int clientSocket = accept(serverSocket, NULL, NULL);

		if (clientSocket < 0) {
			perror("accept failed");
			close(clientSocket);
			exit(EXIT_FAILURE);
		}
		else {
			if (clientSocket > maxSelect) {
				maxSelect = clientSocket;
			}
			
			numberOfClients++;
			response.id++;

			//an array of size 2 to hold the ID and the socket
			std::pair<int, int> idSocket = std::make_pair(response.id, clientSocket);
			clientSockets.push_back(idSocket);

			JoinMsg joinMsg;
			char buffer[256] = { '\0' };
			recv(clientSocket, buffer, sizeof(buffer), NULL);
			memcpy(&joinMsg, buffer, buffer[0]);

			std::cout << joinMsg.name << "Connected" << std::endl;

			response.type = Join;
			send(clientSocket, (char*)&response, sizeof(response), 0);
			response.seq_no++;

			sendNewPlayerMsg(&response.id, joinMsg.name);

			if (numberOfClients == maxClients) {
				startGame();
			}

			if (endGame) {
				break;
			}
		}
	}

}

void Server::startGame() {
	Coordinate coordinate;
	sleep(1);

	std::cout << "Game started!" << std::endl;

	response.type = Change;

	for (int i = 0; i < numberOfClients; i++) {
		int startX;
		int startY;
		int id;

		id = i + 1;

		startX = (rand() % 196) + 2;
		startY = (rand() % 196) + 2;


		coordinate.x = startX;
		coordinate.y = startY;

		std::pair<int, bool> stateID = std::make_pair(id, true);
		playerStates.push_back(stateID);

		grid->movePlayer(coordinate);

		sendNewPosMsg(&id, &coordinate);
	}

	fd_set readSet;
	timeval time;

	while (true)
	{
		int clientID;
		char buffer[512] = { '\0' };

		for (int i = 0; i < clientSockets.size(); i++) 
		{
			int clientSocket = clientSockets.at(i).second;

			FD_ZERO(&readSet);
			FD_SET(clientSocket, &readSet);

			time.tv_sec = 0;
			time.tv_usec = 0;

			int readySocketHandles = select(maxSelect + 1, &readSet, NULL, NULL, &time);

			if (FD_ISSET(clientSocket, &readSet)) {
				recv(clientSocket, buffer, sizeof(buffer), 0);
				MsgHead head;
				memcpy(&head, buffer, sizeof(head));
				
				//increment when a new message is recived
				response.seq_no++;

				switch (head.type)
				{
				case Event: {

					MoveEvent moveEvent;
					memcpy(&moveEvent, buffer, sizeof(moveEvent));
					clientID = moveEvent.event.head.id;

					if (currentState(&clientID)) {
						Coordinate savedMove = move.pos;
						//Coordinate cord = convertToGrid(&move.pos);
						bool availableMove = grid->movePlayer(savedMove);

						if (availableMove) {
							sendNewPosMsg(&clientID, &savedMove);


						}

						else {
							playerLost(&clientID);
							std::cout << "Client " << clientID << " has lost!" << std::endl;

						}

					}
					break;
				}
				case Leave: {
					LeaveMsg leaveMsg;
					memcpy(&leaveMsg, buffer, sizeof(leaveMsg));
					clientID = leaveMsg.head.id;
					sendPlayerLeftMsg(&clientID);
					disconnectPlayer(&clientID);
					numberOfClients--;

					if (numberOfClients == 0) {
						shutDown();
					}
					break;
				}
				}
						 

			}
		}
		if(endGame){
			break;
		}
		
	}
}
	
void Server::sendNewPlayerMsg(unsigned int* id, const char* name) {
	ChangeMsg changeMsg;
	NewPlayerMsg npMsg;

	response.id = *id;
	response.type = Change;

	changeMsg.head = response;
	changeMsg.type = NewPlayer;

	npMsg.msg = changeMsg;
	npMsg.desc = Human;

	strcpy_s(npMsg.name, name);

	for (int i = 0; i < numberOfClients; i++)
	{
		send(clientSockets.at(i).second, (char*)&npMsg, sizeof(npMsg), 0);
		response.seq_no++;
	}
}

void Server::sendNewPosMsg(int* id, Coordinate* pos) {
	ChangeMsg changeMsg;
	NewPlayerPositionMsg nppMsg;

	response.id = *id;
	response.type = Change;

	changeMsg.head = response;
	changeMsg.type = NewPlayerPosition;

	nppMsg.msg = changeMsg;
	nppMsg.pos = *pos;

	for (int i = 0; i < numberOfClients; i++)
	{
		send(clientSockets.at(i).second, (char*)&nppMsg, sizeof(nppMsg), 0);
		response.seq_no++;
	}
}

void Server::sendPlayerLeftMsg(int* id)
{
	ChangeMsg changeMsg;
	PlayerLeaveMsg plMsg;

	response.id = *id;
	response.type = Change;

	changeMsg.head = response;
	changeMsg.type = PlayerLeave;

	plMsg.msg = changeMsg;

	for (int i = 0; i < numberOfClients; i++)
	{
		send(clientSockets.at(i).second, (char*)&plMsg, sizeof(plMsg), 0);
		response.seq_no++;
	}

}

bool Server::currentState(int* id) {
	for (int i = 0; i < playerStates.size(); i++) {
		if (playerStates.at(i).first == *id) {
			return playerStates.at(i).second;
		}
	}

	return false;
}

void Server::playerLost(int* id) {
	for (int i = 0; i < playerStates.size(); i++) {
		if (playerStates.at(i).first == *id) {
			playerStates.at(i).second = false;
		}
	}
}

void Server::disconnectPlayer(int* id) {
	for (int i = 0; i < clientSockets.size(); i++) {
		if (clientSockets.at(i).first == *id) {
			int clientSocket = clientSockets.at(i).second;
			close(clientSocket);
			clientSockets.erase(clientSockets.begin() + i);
		}
	}
}

/*
 closing down socket and cleanup
*/
void Server::shutDown() {
	close(serverSocket);
	delete(grid);
	endGame = true;
}