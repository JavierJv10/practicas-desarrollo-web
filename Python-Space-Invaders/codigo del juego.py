import pygame
import random
import os

# -------------------------
# 0. ASEGURAR RUTA DE TRABAJO
# -------------------------
carpeta_script = os.path.dirname(os.path.abspath(__file__))
# Esto asegura que la ruta de trabajo sea la del script, no la del terminal o IDE, 
# obteniendo la ruta absoluta del archivo .py
os.chdir(carpeta_script)
# buscar en la misma carpeta las imágenes, sin importar desde dónde se ejecute el script
pygame.init()
# inicia todos los modulos del Pygame
ANCHO = 800
ALTO = 600
VENTANA = pygame.display.set_mode((ANCHO, ALTO)) # crea la ventana del juego con el tamaño especificado
pygame.display.set_caption("Space Invaders simple (sprites)")# título de la ventana

BLANCO = (255, 255, 255)
VERDE = (0, 255, 0)
NEGRO = (0, 0, 0)
# CODIGOS DE COLOR : https://codigofacilito.com/articulos/colores-pygame

clock = pygame.time.Clock()# controla la velocidad del juego, limitando los frames por segundo (FPS)

# -------------------------
# 1. CARGA DE IMÁGENES
# -------------------------
fondo = pygame.image.load("fondo.png")
nave_img = pygame.image.load("nave.png")
alien_img = pygame.image.load("alien.png")

# Escalar imágenes a tamaño tipo Space Invaders
nave_img = pygame.transform.scale(nave_img, (64, 64))    # nave pequeña
alien_img = pygame.transform.scale(alien_img, (40, 40))  # alien pequeño
fondo = pygame.transform.scale(fondo, (ANCHO, ALTO))     # fondo ajustado a la ventana

# Tamaños base tomados de la imagen escalada
jugador_ancho, jugador_alto = nave_img.get_rect().size # obtenemos el tamaño de la imagen para usarlo en colisiones y límites de pantalla
enemigo_ancho, enemigo_alto = alien_img.get_rect().size # obtenemos el tamaño de la imagen para usarlo en colisiones y límites de pantalla

# -------------------------
# 2. CONFIGURACIÓN DEL JUGADOR
# -------------------------
jugador_x = ANCHO // 2 - jugador_ancho // 2 # posición horizontal inicial centrada
jugador_y = ALTO - jugador_alto - 20 # posición vertical inicial cerca del borde inferior, dejando un margen de 20 píxeles
jugador_velocidad = 5 # velocidad de movimiento del jugador, ajustable para hacerlo más rápido o lento

# -------------------------
# 3. CONFIGURACIÓN DEL DISPARO
# -------------------------
bala_ancho = 5 # ancho de la bala, ajustable para hacerla más visible o más delgada
bala_alto = 15 # alto de la bala, ajustable para hacerla más visible o más corta
bala_velocidad = 7 
bala_activa = False # indica si la bala está en movimiento o lista para disparar, evitando múltiples balas en pantalla al mismo tiempo
bala_x = 0
bala_y = 0

# -------------------------
# 4. CONFIGURACIÓN DE ENEMIGOS
# -------------------------
num_enemigos = 6
enemigos = []

for i in range(num_enemigos):
    enemigo_x = random.randint(0, ANCHO - enemigo_ancho)
    enemigo_y = random.randint(50, 200)  # franja superior
    enemigo_velocidad_x = 2
    enemigos.append([enemigo_x, enemigo_y, enemigo_velocidad_x])

# -------------------------
# 5. FUNCIONES AUXILIARES
# -------------------------
def dibujar_jugador(x, y):
    VENTANA.blit(nave_img, (x, y))

def dibujar_bala(x, y):
    pygame.draw.rect(VENTANA, VERDE, (x, y, bala_ancho, bala_alto))

def dibujar_enemigo(enemigo):
    x, y, vel_x = enemigo
    VENTANA.blit(alien_img, (x, y))

def hay_colision(rect1, rect2):
    return rect1.colliderect(rect2)

# -------------------------
# 6. BUCLE PRINCIPAL
# -------------------------
ejecutando = True
puntaje = 0
fuente = pygame.font.SysFont(None, 30) 

while ejecutando:
    clock.tick(60) # limita el juego a 60 frames por segundo, ajustable para hacerlo más fluido o más lento

    # Eventos
    for evento in pygame.event.get():  # Recorre la lista de eventos que ocurren en cada frame, como teclas presionadas o cierre de ventana
        if evento.type == pygame.QUIT: # Si el evento es de tipo QUIT (como cerrar la ventana), se detiene el juego
            ejecutando = False # bandera para salir del bucle principal y cerrar el juego

    # Movimiento jugador
    teclas = pygame.key.get_pressed() # obtiene el estado de todas las teclas, lo que permite detectar múltiples teclas presionadas al mismo tiempo
    if teclas[pygame.K_LEFT]: # Si la tecla de flecha izquierda está presionada, se mueve el jugador hacia la izquierda
        jugador_x -= jugador_velocidad # se resta la velocidad al jugador para moverlo a la izquierda
    if teclas[pygame.K_RIGHT]: # Si la tecla de flecha derecha está presionada, se mueve el jugador hacia la derecha
        jugador_x += jugador_velocidad # se suma la velocidad al jugador para moverlo a la derecha

    # Disparo
    if teclas[pygame.K_SPACE]: # Si la barra espaciadora está presionada, se intenta disparar una bala
        if not bala_activa:
            bala_activa = True
            bala_x = jugador_x + jugador_ancho // 2 - bala_ancho // 2 # la bala se posiciona en el centro horizontal del jugador
            bala_y = jugador_y

    # Limitar jugador a la pantalla
    if jugador_x < 0:
        jugador_x = 0
    if jugador_x + jugador_ancho > ANCHO:
        jugador_x = ANCHO - jugador_ancho

    # Movimiento de bala
    if bala_activa:
        bala_y -= bala_velocidad
        if bala_y + bala_alto < 0:
            bala_activa = False

    # Movimiento de enemigos y colisiones
    for enemigo in enemigos:
        enemigo[0] += enemigo[2]

        # Cambio de dirección y bajan
        if enemigo[0] <= 0 or enemigo[0] + enemigo_ancho >= ANCHO:
            enemigo[2] = -enemigo[2]
            enemigo[1] += 20

        jugador_rect = pygame.Rect(jugador_x, jugador_y, jugador_ancho, jugador_alto)
        enemigo_rect = pygame.Rect(enemigo[0], enemigo[1], enemigo_ancho, enemigo_alto)

        # (Opcional) activar muerte por colisión jugador-alien con lo siguiente :  
        # if hay_colision(jugador_rect, enemigo_rect):
        #     ejecutando = False

        if bala_activa:
            bala_rect = pygame.Rect(bala_x, bala_y, bala_ancho, bala_alto)
            if hay_colision(bala_rect, enemigo_rect):
                puntaje += 1
                bala_activa = False
                enemigo[0] = random.randint(0, ANCHO - enemigo_ancho)
                enemigo[1] = random.randint(50, 200)

    # -------------------------
    # DIBUJADO EN PANTALLA
    # -------------------------
    VENTANA.blit(fondo, (0, 0))

    dibujar_jugador(jugador_x, jugador_y)

    if bala_activa:
        dibujar_bala(bala_x, bala_y)

    for enemigo in enemigos:
        dibujar_enemigo(enemigo)

    texto_puntaje = fuente.render(f"Puntos: {puntaje}", True, BLANCO)
    VENTANA.blit(texto_puntaje, (10, 10))

    pygame.display.flip()

pygame.quit()
