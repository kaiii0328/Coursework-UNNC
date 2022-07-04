#pragma once
#include "DisplayableObject.h"
#include "Input.h"
#include "Animation.h"

class FerrisWheel : public DisplayableObject, public Input, public Animation
{
public:
	FerrisWheel() {};
	~FerrisWheel() {};

	void Display();
	void drawWheel();
	float Deg2Rad(float deg);
	void HandleKey(unsigned char key, int state, int x, int y);
	void Update(const double& deltaTime);
	void drawCabins();
	void drawBars();
	void drawBase();

private:
	GLfloat centerRadius = 10;
	GLfloat centerLength = 80;
	// radius for the outside wheel
	GLfloat radius_outside = 60;
	//radius for the inside wheel
	GLfloat radius_inside = 54;
	GLfloat frontWheelPos;
	GLfloat backWheelPos;
	// rotated angle
	GLfloat rot_angle = 0;
	// rotation speed
	GLfloat rot_speed = .5f;
};
