#pragma once
#include "DisplayableObject.h"
#include "Animation.h"

class Dog : public DisplayableObject, public Animation {
public:
	Dog() {
		// Get the texture 	
		textureId = Scene::GetTexture(texure_path);
		furTextureId = Scene::GetTexture(fur_texture_path);
		pos[0] = 0;
		pos[1] = -10;
		pos[2] = -20;
		srand(12903823);
	};
	~Dog() {};

	void Display();
	void Update(const double& deltaTime);

	int textureId = 0;
	int furTextureId = 0;
private:
	void DrawHead(float R);
	void DrawFrontLegs(float length, float r);
	void DrawBackLegs(float length, float r);
	void DrawTails(float length, float r);
	void DrawBody();
	void clearDirStates();

	// file path of the textures
	std::string texure_path = "Textures/cat_face2.bmp";
	std::string fur_texture_path = "Textures/fur.bmp";

	float R = 1.f;  // head
	float body_width = 1.5f;
	float body_length = 1.f;
	float body_height = 2.5f;
	float leg_r = 0.5f;
	float leg_length = 2.f;

	float leg_angle = 20.0f;

	int rev = 1;
	float animation_speed = 4.f;
	float walk_speed = .5f;

	GLboolean walking = false;
	GLfloat x_max_pos = 30, x_max_neg = -50, z_max_pos = 0, z_max_neg = -50;
	GLboolean up = false, down = false, left = false, right = false;
};