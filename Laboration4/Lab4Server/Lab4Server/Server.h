#pragma once
#include <iostream>
#include <WS2tcpip.h>
#include <WinSock2.h>
#include "GameProtocol.h"
#include <vector>
#include <string>

class GameGrid {

private:
	


public:
	bool movePlayer(Coordinate position);
	GameGrid();
	
};

class Server {
private:
	SOCKET s;
	bool endGame;
public:
	void initiate(int portNum, int maxClients);
	void start();
	void shutDown();
	void sendNewPlayerMsg(unsigned int* id, const char* name);
	void sendNewPosMsg(int* id, Coordinate* pos);
	void sendPlayerLeft(int* id);
	bool currentState(int* id);



};
