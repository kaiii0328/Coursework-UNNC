#pragma once


#include "DisplayableObject.h"
#include "Animation.h"


class PirateShip : public DisplayableObject, public Animation
{
public:
	
	PirateShip() {
		init();
	};
	~PirateShip() {};

	void Display();
	float Deg2Rad(float deg);
	void Update(const double& deltaTime);
	void drawBase();
	void init();

private:
	GLfloat centerRadius = 10;
	GLfloat centerLength = 80;
	GLfloat radius_outside = 60;
	GLfloat radius_inside = 54;
	GLfloat frontWheelPos = 20;
	GLfloat backWheelPos = -20;
	GLfloat rot_angle = 0;
	GLfloat rot_speed = 1.f;
	bool rev = false;
	std::string filepath = "Model/boat3.obj";
	
};
