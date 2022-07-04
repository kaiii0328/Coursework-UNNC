#pragma once
#include "VectorClasses.h"  
#include "DisplayableObject.h"
#include "Animation.h"


class Fluid : public DisplayableObject, public Animation
{
private:

	long            width; 
	long            height; 

	Vector3D* buffer[2]; 
	long            renderBuffer;  //current render buffer

	Vector3D* normal; 
	Vector3D* tangent; 

	int* indices[3]; //indices for textures
	float* texcoords[3][2]; //
	float           k1, k2, k3; //coefficients

	int texture;

	int cnt = 0;
	float matAmbient[4];      // matrial properties of the grid
	float matDiffuse[4];
	int   matShininess;
	float matSpecular[4];
public:

	//n:width m: hight d£ºdistance between two vertices
	//time:wave time c£ºwavespeed mu£ºcoefficients for frictions: tex: id for loaded texture

	Fluid(long n, long m, float dist, float time, float wavespeed, float mu, int tex);
	~Fluid();

	void Evaluate(void);
	void Display();
	void Update(const double& deltaTime);
};