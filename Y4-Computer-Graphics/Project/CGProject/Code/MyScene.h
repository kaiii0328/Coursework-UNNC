#pragma once

#include "../Framework/Engine/Scene.h"


class MyScene :
	public Scene
{
public:
	MyScene(int argc, char** argv, const char* title, const int& windowWidth, const int& windowHeight);
	~MyScene() {};

	glm::mat4 GetProjMatrx() {
		return projMatrx;
	}

	glm::mat4 projMatrx;

private:
	void Initialise();
	void Projection();
	

};