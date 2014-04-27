#ifdef GL_ES 
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

#define PI 3.14

uniform float u_time;

varying vec2 v_texCoord;
varying float v_distance;
varying vec2 v_position;

float hash(vec2 p)
{
	return fract(sin(dot(p, vec2(32.3391, 38.5373))) * 74638.5453);
}

void main() 
{
	vec3 c = vec3(0.0, 1.0, 1.0);
	
	float flick = max(1.0-sin(fract(u_time) * PI), 0.0) * 0.03;
	c += hash(v_position) * 0.35 - flick;
	
	//c += vec3(0.2, 0.9, 0.1) * mod(abs(tan(v_position.x * PI / 15.0)), 5.0);

	gl_FragColor = vec4(c, 1.0);
	gl_FragColor.a *= clamp(1.5 - v_distance / 100.0, 0.0, 1.0);
}