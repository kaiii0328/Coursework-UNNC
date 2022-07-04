#include "Dog.h"

void Dog::Display() {
	glPushMatrix();
	
	glScalef(8,8,8);
	glTranslatef(pos[0], pos[1], pos[2]);
	// rotations around the xyz axises
	glRotatef(rotation[1], 0.0f, 1.0f, 0.0f); 
	glRotatef(rotation[2], 0.0f, 0.0f, 1.0f); 
	glRotatef(rotation[0], 1.0f, 0.0f, 0.0f); 

	//draw dog
	// Head
	glClearColor(0, 0, 0, 0);
	glTranslatef(0.f, 2.f, 0.f);
	DrawHead(R);
	glTranslatef(0.f, -R, 0.f);

	// front legs
	glPushMatrix();
	DrawFrontLegs(leg_length, leg_r);
	glPopMatrix();

	// body
	glPushMatrix();
	DrawBody();
	glPopMatrix();

	//go the back of the dog to draw back legs
	glTranslatef(0.f, -body_length / 2.f, 0.f);

	// back legs
	glPushMatrix();
	DrawBackLegs(leg_length, leg_r);
	glPopMatrix();

	//draw tail
	glPushMatrix();
	DrawTails(leg_length, leg_r / 2.f);
	glPopMatrix();
	glPopMatrix();
}

void Dog::DrawHead(float R) {
	glPushMatrix();

	//apply the face texture on the sphere
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, textureId);

	glColor3f(1, 0.75, 0.7);
	glRotatef(-90.f, 1.f, 0.f, 0.f);
	GLUquadricObj* head = gluNewQuadric();
	gluQuadricDrawStyle(head, GLU_FILL);
	gluQuadricTexture(head, GL_TRUE);
	gluQuadricNormals(head, GLU_SMOOTH);
	gluSphere(head, R, 10, 10);
	glRotatef(90.f, 1.f, 0.f, 0.f);


	//draw ears
	glPushMatrix();
	glTranslatef(-R / 2.f, 2*R/3, R / 5.f);
	glScalef(1.f, 3.f, 1.f);
	glRotatef(28, 0, 0, 1);
	glRotatef(90, -1, 0, 0);
	glutSolidCone(R / 8.f, R / 4, 50, 10);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(R / 2.f, 2*R/3, R / 5.f);
	glScalef(1.f, 3.f, 1.f);
	glRotatef(-28, 0, 0, 1);
	glRotatef(90, -1, 0, 0);
	glutSolidCone(R / 8.f, R/4,50,10);
	glPopMatrix();

	glBindTexture(GL_TEXTURE_2D, NULL);
	glDisable(GL_TEXTURE_2D);

	glPopMatrix();
}


void Dog::DrawFrontLegs(float length, float r) {
	glColor3f(0.55, 0.26, 0.18);
	// draw the left front leg
	glPushMatrix();
	glTranslatef(0.f, -0.25f, 0.0f);
	glRotatef(leg_angle, 1.0f, 0.f, 0.f);
	glTranslatef(-body_width / 2.f, -leg_length / 4.f, 0.f);

	// apply fur texture
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, furTextureId);

	glScalef(r, length, r);
	glutSolidCube(1.f);
	glPopMatrix();

	//draw the right front leg
	glPushMatrix();
	glRotatef(180.f, 0.f, 1.f, 0.f);
	glTranslatef(0.f, -0.25f, 0.0f);
	glRotatef(leg_angle, 1.0f, 0.f, 0.f);
	glTranslatef(-body_width / 2.f, -leg_length / 4.f, 0.f);

	glScalef(r, length, r);
	glutSolidCube(1.f);

	glBindTexture(GL_TEXTURE_2D, NULL);
	glDisable(GL_TEXTURE_2D);
	glPopMatrix();
}

void Dog::DrawBackLegs(float length, float r) {
	//draw the left back leg
	glPushMatrix();
	glTranslatef(-body_width / 2.f, 0.f, -body_height);
	glRotatef(-leg_angle, 1.0f, 0.f, 0.f);
	glScalef(r, length, r); // set scale of this part of the leg
	
	// apply fur texture
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, furTextureId);
	glutSolidCube(1.f);	
	glPopMatrix();

	//draw the right back leg
	glPushMatrix();
	glRotatef(180.f, 0.f, 1.f, 0.f);
	glTranslatef(-body_width / 2.f , 0.f, body_height);
	glRotatef(-leg_angle, 1.0f, 0.f, 0.f);
	glScalef(r, length, r);
	glutSolidCube(1.f);
	
	glBindTexture(GL_TEXTURE_2D, NULL);
	glDisable(GL_TEXTURE_2D);
	glPopMatrix();
}

void Dog::DrawTails(float length, float r) {
	//draw the tail
	glPushMatrix();
	glTranslatef(-body_width / 2.f, 3*body_height/4, -body_width / 3.f);
	glTranslatef(body_width / 2.f, -0.5f, -7*body_height/8);
	glRotatef(-20, 1.0f, 0.f, 0.f);
	glScalef(r, 3*length/4, r); 

	// apply fur texture
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, furTextureId);
	glutSolidCube(1.f);
	glBindTexture(GL_TEXTURE_2D, NULL);
	glDisable(GL_TEXTURE_2D);
	glPopMatrix();
}

void Dog::DrawBody() {	
	//draw the body
	glTranslatef(0.f, 0.f, -body_height / 2.f);
	glRotatef(-90.f, 1.f, 0.f, 0.f);
	glScalef(body_width, body_height, body_length);

	// apply fur texture
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, furTextureId);
	glutSolidCube(1.f);

	glBindTexture(GL_TEXTURE_2D, NULL);
	glDisable(GL_TEXTURE_2D);
}



void Dog::Update(const double& deltaTime) {
	// randomly move aournd the restricted areas in the park
	int move = rand() % 32;
	if (move == 0) {
		// randomly clear the flags for each directions
		clearDirStates();
	}

	if (leg_angle > 30 || leg_angle < -30)
		rev *= -1;

	leg_angle += rev * animation_speed;		

	int direction = rand() % 256;

	if (direction == 0) // move upward: negative z axis
		up = true;
	else if (direction == 1) //move downwards: positive z axis
		down = true;
	else if (direction == 2) // move towards left
		left = true;
	else if (direction == 3) // move towards right
		right = true;
	else if (direction == 4) { // move towards up-left diagonal 
		up = true;
		left = true;
	}
	else if (direction == 5) { // move towards up-right diagonal
		up = true;
		right = true;
	}
	else if (direction == 6) { // move towards down-left diagonanl
		down = true;
		left = true;
	}
	else if (direction == 7) { // move towards down-right diagonal
		down = true;
		right = true;
	}

	// move the dog if it is in the region
	if (up) {
		if (pos[2] - walk_speed > z_max_neg)
			pos[2] -= walk_speed;
		rotation[1] = 180;
	}
	if (down) {
		if (pos[2] + walk_speed < z_max_pos)
			pos[2] += walk_speed;
		rotation[1] = 0;
	}
	if (left) {
		if (pos[0] - walk_speed > x_max_neg)
			pos[0] -= walk_speed;
		rotation[1] = 270;
	}
	if (right) {
		if (pos[0] + walk_speed < x_max_pos)
			pos[0] += walk_speed;
		rotation[1] = 90;
	}
	if (up && right)
		rotation[1] = 135.f;
	
	if (up && left)
		rotation[1] = 215.f;
	
	if (down && right) 
		rotation[1] = 45.f;
	
	if (down && left) 
		rotation[1] = 315.f;
}


void Dog::clearDirStates()
{
	up = false; down = false;
	right = false;  left = false;
}