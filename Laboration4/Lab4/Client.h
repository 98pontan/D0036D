#pragma once
#pragma comment(lib, "Ws2_32.lib")

#include <iostream>
#include <WinSock2.h>
#include <WS2tcpip.h>
#include "GameProtocol.h"

#define MAXNAMELEN 32

class Client {
private:

	//Last player coordinates
	Coordinate position;
	//default header for messages
	MsgHead thisHeader;
	//Socket
	SOCKET s;
	//Boolean for stoping the listening loop
	bool stopListening;
	//Boolean to know if a player has a position
	bool setPosition;
	//sends a move event
	void move(Coordinate xy);
	//sends a leave event
	void leave();

public:
	void serverConnector(std::string ipAdress, int portNum);

	void listener();

};

class GUIcommunication {
private:
	//! The UDP socket
	SOCKET guiSocket;
	//! Server hint structure
	sockaddr_in server;
public:
	GUIcommunication();
	~GUIcommunication();
	void repaint(unsigned int id, Coordinate pos);
};
