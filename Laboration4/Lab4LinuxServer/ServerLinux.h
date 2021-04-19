#pragma once
#pragma once
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <iostream>
#include "GameProtocol.h"
#include <thread>
#include <vector>
#include <string>

class GameGrid {

private:
	bool** grid;


public:
	bool movePlayer(Coordinate position);
	GameGrid();
	~GameGrid();

};

class Server {
private:
	//SOCKET serverSocket;
	bool endGame;

public:
	void initiate(int portNum, int maxClients);
	void start();
	void shutDown();
	void sendNewPlayerMsg(unsigned int* id, const char* name);
	void sendNewPosMsg(int* id, Coordinate* pos);
	void sendPlayerLeftMsg(int* id);
	void playerLost(int* id);
	void disconnectPlayer(int* id);
	bool currentState(int* id);




};