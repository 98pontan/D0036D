#include "Client.h"
#include "sendGame.h"
#include <iostream>

int main() {
	Client client;

	client.serverConnector("127.0.0.1", 5000);
}

void Client::serverConnector(std::string ipAdress, int portNum) {

	WSADATA wsdata;
	int wsCheck;
	WORD ver;
	int portNum = 54000;
	int connected;
	sockaddr_in target; 

	MsgHead response;


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
	
	target.sin_family = AF_INET;
	target.sin_port = htons(portNum);
	inet_pton(AF_INET, ipAdress.c_str(), &target.sin_addr);

	connected = SOCKET_ERROR;

	connected = connect(s, (sockaddr*)&target, sizeof(target));
	if (connected != SOCKET_ERROR) {
		std::cout << "Succesfull connection!" << std::endl;
	}
	else {
		std::cout << "Failed connection" << std::endl;
		return;
	}

	// Join message
	JoinMsg jMsg{
		MsgHead{sizeof jMsg, 0, 0, Join},
		Human,
		Cube,
		"HelloWorld"
	};

	send(s, (char*)&jMsg, sizeof(jMsg), 0);

	char buffer[256] = { '\0*' };
	recv(s, buffer, sizeof(buffer), 0);
	
	memcpy(&response, buffer, buffer[0]);
	thisHeader.id = response.id;
	
}

void Client::listener() {
	GUIcommunication guiCom;
	fd_set readSet;
	timeval time;
	MsgHead msgHead;
	ChangeMsg eventMessage;
	int validSockets;
	short pressedKey;



	while(true)
	{
		
		if (stopListening) 
			break;

		time.tv_sec = 0;
		time.tv_usec = 0;
		
		FD_ZERO(&readSet);
		FD_SET(s, &readSet);

		validSockets = select(0, &readSet, NULL, NULL, &time);

		char buffer[512] = { '\0' };

		if (validSockets == SOCKET_ERROR)
		{
			std::cout << "Error! Select failed " << WSAGetLastError() << std::endl;
			break;
		}

		else if (FD_ISSET(s, &readSet)) 
		{
			recv(s, buffer, sizeof(buffer), 0);
			memcpy(&msgHead, buffer, sizeof(msgHead));

			if (msgHead.type == Change)
			{
				switch (eventMessage.type)
				{
				case NewPlayer: {
					NewPlayerMsg npMsg;
					memcpy(&npMsg, buffer, sizeof(npMsg));
					std::cout << "New player: " << npMsg.name << "ID: " << msgHead.id << "Has joined!" << std::endl;
					break;
				}
				
				case NewPlayerPosition: {
					NewPlayerPositionMsg nppMsg;
					memcpy(&nppMsg, buffer, sizeof(nppMsg));
					if (msgHead.id == thisHeader.id)
					{
						position.x = nppMsg.pos.x;
						position.y = nppMsg.pos.y;
						std::cout << "New position X: " << position.x << "New position Y: " << position.y << std::endl;

					}
					else
						std::cout << "ID: " << msgHead.id << "Position X: " << nppMsg.pos.x << "Position Y: " << nppMsg.pos.y << std::endl;

					guiCom.repaint(msgHead.id, nppMsg.pos);
					setPosition = true;
					break;
				}
				case PlayerLeave: {
					PlayerLeaveMsg plMsg;
					memcpy(&plMsg, buffer, sizeof(plMsg));
					std::cout << "ID: " << msgHead.id << " Has left the game" << std::endl;
					break;
				}

				}

			}

		}

	
		if (setPosition) {
			if (GetAsyncKeyState(VK_UP) & 1)
				position.y--;


			else if (GetAsyncKeyState(VK_DOWN) & 1) {
				position.y++;
				move(position);
			}
				

			else if (GetAsyncKeyState(VK_RIGHT) & 1){ 
				position.x++;
				move(position);
			}
			else if (GetAsyncKeyState(VK_LEFT) & 1)
			{
				position.x--;
				move(position);
			}

			else if (GetAsyncKeyState('Q') & 1)
			{
				leave();
			}
		}

	}
}	

void Client::move(Coordinate xy) {
	EventMsg eveMsg;
	MoveEvent mEve;

	thisHeader.type = Event;

	eveMsg.type = Move;
	eveMsg.head = thisHeader;

	mEve.event = eveMsg;
	mEve.pos = xy;

	send(s, (char*)&mEve, sizeof(mEve), 0);
}

void Client::leave() {
	LeaveMsg lMsg;

	thisHeader.type = Leave;

	lMsg.head = thisHeader;

	send(s, (char*)&lMsg, sizeof(lMsg), 0);


	stopListening = true;

	Client::~Client();

}

Client::~Client() {
	WSACleanup();
	closesocket(s);
	return;
}
