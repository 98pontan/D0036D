#include <iostream>
#include <WinSock2.h>
#include <WS2tcpip.h>
#include "Server.h"

int numberOfClients;
GameGrid* grid;
MsgHead response;
bool endGame = false;

/*
  creates an object os type Server and calls the initiate function to start the server
*/
int main() {
	Server server;
	server.initiate(5300, 1);
}

//Same as java arrayList
std::vector<std::pair<int, bool>> playerStates;
std::vector<std::pair<int, SOCKET>> clientSockets;

/*
  Initlizes a socket for the server and listens for clients, connects the client sockets to the server, sends messages and saves the client sockets a list. 
*/
void Server::initiate(int portNum, int maxClients) 
{

	WSADATA wsdata;
	int wsCheck;
	WORD ver;
	int connected;
	sockaddr_in server;
	sockaddr_in client;
	JoinMsg jMsg;
	


	ver = MAKEWORD(2, 2);
	wsCheck = WSAStartup(ver, &wsdata);

	if (wsCheck != 0) {
		std::cerr << "cant initialize" << std::endl;
		return;
	}

	// Socket
	s = socket(AF_INET, SOCK_STREAM, 0);

	if (s == INVALID_SOCKET) {
		std::cerr << "cant create socket" << std::endl;
		return;
	}

	server.sin_family = AF_INET;
	server.sin_port = htons(portNum);
	server.sin_addr.S_un.S_addr = INADDR_ANY;

	// Bind the socket and listen to it
	bind(s, (sockaddr*)&server, sizeof(server));
	listen(s, SOMAXCONN);

	int sizeOfClient = sizeof(client);

	numberOfClients = 0;

	while (true) {
		SOCKET clientSocket = socket(AF_INET, SOCK_STREAM, 0);

		if (clientSocket == SOCKET_ERROR) {
			std::cout << "Socket error!" << std::endl;
			return;
		}

		if (clientSocket = accept(s, (sockaddr*)&client, &sizeOfClient)) {
			response.id++;
			numberOfClients++;
			std::pair<int, SOCKET> socketID = std::make_pair(response.id, clientSocket);
			clientSockets.push_back(socketID);


			// recive join msg
			char buffer[256] = { '\0*' };
			recv(clientSocket, buffer, sizeof(buffer), 0);
			memcpy(&jMsg, buffer, buffer[0]);
			char buffer[255] = { '\0*' };

			std::cout << jMsg.name << "has joined the server" << std::endl;

			// response msg
			response.type = Join;
			send(clientSocket, (char*)&response, sizeof(response), 0);
			response.seq_no++;
			sendNewPlayerMsg(&response.id, jMsg.name);

			if (numberOfClients == maxClients)
				start();

			else if (endGame)
				break;
		}
	}
}

void Server::start() {
	int startX;
	int startY;
	int id;
	int clientID;
	fd_set readSet;
	timeval time;
	Coordinate coordinate;
	SOCKET client;
	MsgHead msgHead;

	std::cout << "Welcome to the game!" << std::endl;

	grid = new GameGrid();

	response.type = Change;

	for (int i = 0; i < numberOfClients; i++) 
	{
		id = i + 1;
		
		startX = (rand() % 196)+2;
		startY = (rand() % 196)+2;

		
		coordinate.x = startX;
		coordinate.y = startY;

		std::pair<int, bool> stateID = std::make_pair(id, true);
		playerStates.push_back(stateID);

		grid->movePlayer(coordinate);
		
		sendNewPosMsg(&id, &coordinate);

	}

	// listen to all clients
	while (true)
	{
		char buffer[512] = { '\0' };

		for (int i = 0; i < clientSockets.size(); i++) {

			FD_ZERO(&readSet);
			client = clientSockets.at(i).second;
			FD_SET(client, &readSet);

			time.tv_sec = 0;
			time.tv_usec = 0;

			int readySocketHandles = select(0, &readSet, NULL, NULL, &time);

			if (client, &readSet) {
				recv(client, buffer, sizeof(buffer), 0);
				memcpy(&msgHead, buffer, sizeof(msgHead));

				response.seq_no++;

				switch (msgHead.type)
				{
				case Event:
					MoveEvent move;
					clientID = move.event.head.id;
					memcpy(&move, buffer, sizeof(move));

					if(currentState)
					break;
				}
			}
		}
		
	}
	

}

void Server::sendNewPlayerMsg(unsigned int* id, const char* name) 
{
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

void Server::sendPlayerLeft(int* id) 
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