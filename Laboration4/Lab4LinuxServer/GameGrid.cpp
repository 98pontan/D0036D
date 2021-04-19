#include "ServerLinux.h"

GameGrid::GameGrid() {
	int size = 201;
	grid = new bool* [size];

	for (int i = 0; i < size; i++) {
		grid[i] = new bool[size];
	}

	for (int x = 0; x < 201; x++) {
		for (int y = 0; y < 201; y++) {
			grid[x][y] = false;
		}
	}
}

/*
* clears memory
*/
GameGrid::~GameGrid() {
	for (int i = 0; i < 201; i++) {
		delete[] grid[i];
	}
	delete[] grid;
}

bool GameGrid::movePlayer(Coordinate cor) {

	// Check out of bounds
	if (cor.x <= 1 || cor.x >= 199 || cor.y <= 1 || cor.y >= 199) {
		return false;
	}
	// Check occupied
	else {
		if (grid[cor.x][cor.y] == false) {
			grid[cor.x][cor.y] = true;
			return true;
		}
		return false;
	}
}