#include <ModelLoader.h>
#include "ItineraryBoat.h"


Model iBoatModel;
void ItineraryBoat::Display()
{
	glPushMatrix();

	// draw bridge
	glTranslatef(xPos, -80, 180 + zPos);
	glRotated(rot_angle, 0, 1, 0);
	glScalef(15, 15, 15);
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	float mat_ambient[] = { 0.49, 0.29, 0.29, 1.0f };
	float mat_diffuse[] = { 0.49, 0.29, 0.29, 1.0f };
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float mat_shininess[] = { 60.0 }; // 0-128

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glColor3f(0.49, 0.29, 0.29);
	iBoatModel.draw();
	glPopAttrib();
	glPopMatrix();

}

void ItineraryBoat::init()
{
	iBoatModel.load(modelpath.c_str());
}

void ItineraryBoat::Update(const double& deltaTime)
{
	if (toLeft) {
		xPos -= speed;
	}
	else if (downwards) {
		zPos += speed;
	}
	else if (toRight)
	{
		xPos += speed;
	}
	else if (forwards)
	{
		zPos -= speed;
	}

	if (xPos < -300) {
		toLeft = false;
		downwards = true;
		xPos = -300;
		rot_angle = 0;
	}

	if (zPos > 60) {
		downwards = false;
		toRight = true;
		zPos = 60;
		rot_angle = 90;
	}

	if (xPos > 200) {
		toRight = false;
		forwards = true;
		xPos = 200;
		rot_angle = 180;
	}

	if (zPos < 0) {
		forwards = false;
		toLeft = true;
		zPos = 0;
		rot_angle = -90;
	}
}
