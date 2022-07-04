#include "Sky.h"
#include "MyScene.h"
#include "Floor.h"
#include "PirateShip.h"
#include "Bridge.h"
#include "FerrisWheel.h"
#include "Fluid.h"
#include "Fence.h"
#include "Gate.h"
#include "ItineraryBoat.h"
#include "Dog.h"

MyScene::MyScene(int argc, char** argv, const char *title, const int& windowWidth, const int& windowHeight)
	: Scene(argc, argv, title, windowWidth, windowHeight){}

void MyScene::Initialise()
{
	// set the background colour of the scene to white
	//glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	glm::mat4 viewMatrix = camera.GetViewMatrix();
	Sky* sky = new Sky(100.f, projMatrx, viewMatrix);
	sky->position(-100, -120, 0);
	sky->size(650);
	AddObjectToScene(sky);

	Dog* dog = new Dog();
	AddObjectToScene(dog);

	std::string waterTexpath = "Textures/water.bmp";
	int waterTextureId = texture.GetTexture(waterTexpath.c_str());
	Fluid* fl = new Fluid(51, 51, 2, 1, 1.2, 0, waterTextureId);
	AddObjectToScene(fl);

	Fence* fe = new Fence();
	AddObjectToScene(fe);

	Gate* gt = new Gate();
	AddObjectToScene(gt);

	std::string grassTexpath = "Textures/grass3.bmp";
	int grassTextureId = texture.GetTexture(grassTexpath.c_str());
	Floor *f = new Floor(grassTextureId);
	f->size(100.0f);
	AddObjectToScene(f);

	Bridge* br = new Bridge();
	AddObjectToScene(br);

	FerrisWheel* fw = new FerrisWheel();
	AddObjectToScene(fw);

	PirateShip* ps = new PirateShip();
	AddObjectToScene(ps);

	ItineraryBoat* it = new ItineraryBoat();
	AddObjectToScene(it);	
}

/// set the perspective of camera
void MyScene::Projection()
{
    GLdouble aspect = static_cast<GLdouble>(windowWidth) / static_cast<GLdouble>(windowHeight);
    gluPerspective(60.0, aspect, 1.0, 2600.0);
	projMatrx = glm::perspective(60.0f, (GLfloat)aspect, 1.f, 2600.0f);
}
