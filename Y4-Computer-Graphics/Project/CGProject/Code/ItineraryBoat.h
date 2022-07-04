#pragma once
#include "DisplayableObject.h"
#include "Animation.h"
class ItineraryBoat : public DisplayableObject, public Animation
{
public:

	ItineraryBoat() {
		init();
	};
	~ItineraryBoat() {};

	void Display();
	void init();
	void Update(const double& deltaTime);
private:
	std::string modelpath = "Model/itineraryboat.obj";
	GLfloat xPos = 200;
	GLfloat zPos = 0;
	GLfloat speed = 1.5f;
	GLfloat rot_angle = -90;

	bool toLeft = true;
	bool downwards = false;
	bool toRight = false;
	bool forwards = false;
};