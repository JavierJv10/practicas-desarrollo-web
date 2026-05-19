-- =====================================================================
-- 20 CONSULTAS SOBRE JOINS (INNER, LEFT, RIGHT, FULL)
-- Base de datos: Sakila (MySQL)
-- =====================================================================

-- =====================================================================
-- EXPLICACIÓN DE LOS TIPOS DE JOIN
-- =====================================================================

/*
1. INNER JOIN (Intersección)
   - Devuelve SOLO los registros que tienen coincidencias en AMBAS tablas
   - Si un registro de la tabla A no tiene coincidencia en la tabla B, NO aparece
   - Si un registro de la tabla B no tiene coincidencia en la tabla A, NO aparece
   - Es el JOIN más restrictivo
   
   Ejemplo visual:
   Tabla A: [1, 2, 3, 4]
   Tabla B: [3, 4, 5, 6]
   INNER JOIN: [3, 4] ← Solo los valores que existen en AMBAS

2. LEFT JOIN (LEFT OUTER JOIN)
   - Devuelve TODOS los registros de la tabla IZQUIERDA (primera tabla)
   - Y los registros coincidentes de la tabla DERECHA
   - Si un registro de la izquierda no tiene coincidencia a la derecha, 
     se muestran valores NULL para las columnas de la derecha
   - Útil para encontrar "registros huérfanos" o ver qué falta
   
   Ejemplo visual:
   Tabla A (izquierda): [1, 2, 3, 4]
   Tabla B (derecha): [3, 4, 5, 6]
   LEFT JOIN: [1(NULL), 2(NULL), 3(3), 4(4)] ← Todos de A + coincidencias de B

3. RIGHT JOIN (RIGHT OUTER JOIN)
   - Devuelve TODOS los registros de la tabla DERECHA (segunda tabla)
   - Y los registros coincidentes de la tabla IZQUIERDA
   - Si un registro de la derecha no tiene coincidencia a la izquierda,
     se muestran valores NULL para las columnas de la izquierda
   - Es lo opuesto al LEFT JOIN
   
   Ejemplo visual:
   Tabla A (izquierda): [1, 2, 3, 4]
   Tabla B (derecha): [3, 4, 5, 6]
   RIGHT JOIN: [3(3), 4(4), 5(NULL), 6(NULL)] ← Coincidencias de A + todos de B

4. FULL OUTER JOIN (FULL JOIN)
   - Devuelve TODOS los registros cuando hay coincidencia en cualquiera de las tablas
   - Combina LEFT JOIN y RIGHT JOIN
   - Muestra TODO: coincidencias + no coincidencias de ambas tablas
   - NOTA: MySQL no soporta FULL OUTER JOIN directamente, se simula con UNION
   
   Ejemplo visual:
   Tabla A: [1, 2, 3, 4]
   Tabla B: [3, 4, 5, 6]
   FULL JOIN: [1(NULL), 2(NULL), 3(3), 4(4), 5(NULL), 6(NULL)] ← TODOS

RESUMEN RÁPIDO:
- INNER JOIN = Solo coincidencias (∩)
- LEFT JOIN = Todo de la izquierda + coincidencias
- RIGHT JOIN = Coincidencias + todo de la derecha
- FULL JOIN = TODO de ambas tablas
*/



/*
CUÁNDO USAR CADA TIPO DE JOIN:

INNER JOIN - Usa cuando:
   - Solo quieres datos COMPLETOS y VÁLIDOS
   - Necesitas garantizar que ambas tablas tienen información
   - Ejemplo: "Listar ventas con cliente Y producto válidos"
   - Ejemplo: "Empleados que tienen asignación de proyecto"

LEFT JOIN - Usa cuando:
   - Quieres TODOS los registros de la tabla principal
   - Necesitas encontrar "huérfanos" (registros sin relación)
   - Ejemplo: "Todos los clientes, hayan comprado o no"
   - Ejemplo: "Productos sin ventas" (WHERE venta_id IS NULL)
   - Ejemplo: "Empleados con o sin proyecto asignado"

RIGHT JOIN - Usa cuando:
   - Quieres TODOS los registros de la tabla secundaria
   - Es menos común, normalmente se prefiere LEFT JOIN reorganizando tablas
   - Ejemplo: "Todos los productos, se hayan vendido o no"
   - Útil cuando la consulta se lee mejor así


FULL OUTER JOIN - Usa cuando:
   - Necesitas AUDITORÍA COMPLETA de ambas tablas
   - Quieres encontrar inconsistencias en AMBAS direcciones
   - Ejemplo: "Clientes sin pedidos Y pedidos sin cliente"
   - Ejemplo: "Productos sin ventas Y ventas de productos eliminados"
   - Ejemplo: "Reconciliación de datos entre sistemas"

TIPS DE RENDIMIENTO:
- INNER JOIN es generalmente el más rápido
- LEFT/RIGHT JOIN pueden ser más lentos con tablas grandes
- FULL JOIN (UNION) es el más costoso
- Usa índices en las columnas de JOIN para mejor rendimiento
- Filtra con WHERE después del JOIN cuando sea posible
*/











-- =====================================================================
-- SECCIÓN 1: INNER JOIN (5 consultas)
-- Muestra SOLO registros con coincidencias en ambas tablas
-- =====================================================================

-- 101. Películas con actores (INNER JOIN)
-- Solo muestra películas que TIENEN actores asignados
SELECT 
    f.film_id,
    f.title AS pelicula,
    a.first_name,
    a.last_name
FROM film f
INNER JOIN film_actor fa ON f.film_id = fa.film_id
INNER JOIN actor a ON fa.actor_id = a.actor_id
ORDER BY f.title, a.last_name
LIMIT 50;
-- Explicación: Si hubiera una película sin actores, NO aparecería en el resultado


-- 102. Clientes con sus pagos (INNER JOIN)
-- Solo muestra clientes que HAN REALIZADO pagos
SELECT 
    c.customer_id,
    c.first_name,
    c.last_name,
    p.payment_id,
    p.amount,
    p.payment_date
FROM customer c
INNER JOIN payment p ON c.customer_id = p.customer_id
ORDER BY c.last_name, p.payment_date DESC
LIMIT 100;
-- Explicación: Si un cliente nunca ha pagado, NO aparece en el resultado


-- 103. Inventario con películas y tiendas (INNER JOIN)
-- Solo muestra inventario que ESTÁ VINCULADO a películas y tiendas
SELECT 
    i.inventory_id,
    f.title AS pelicula,
    s.store_id,
    CONCAT(st.first_name, ' ', st.last_name) AS gerente_tienda
FROM inventory i
INNER JOIN film f ON i.film_id = f.film_id
INNER JOIN store s ON i.store_id = s.store_id
INNER JOIN staff st ON s.manager_staff_id = st.staff_id
ORDER BY s.store_id, f.title
LIMIT 50;
-- Explicación: Solo muestra registros de inventario completos y válidos


-- 104. Alquileres con información completa del cliente y la película (INNER JOIN)
-- Solo alquileres con TODA la información disponible
SELECT 
    r.rental_id,
    r.rental_date,
    CONCAT(c.first_name, ' ', c.last_name) AS cliente,
    f.title AS pelicula_alquilada,
    f.rental_rate AS precio_alquiler
FROM rental r
INNER JOIN customer c ON r.customer_id = c.customer_id
INNER JOIN inventory i ON r.inventory_id = i.inventory_id
INNER JOIN film f ON i.film_id = f.film_id
ORDER BY r.rental_date DESC
LIMIT 100;
-- Explicación: Excluye alquileres con datos faltantes o inconsistentes


-- 105. Direcciones completas de clientes (INNER JOIN)
-- Solo clientes con dirección, ciudad y país COMPLETOS
SELECT 
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS cliente,
    a.address,
    a.district,
    ci.city,
    co.country,
    a.postal_code,
    a.phone
FROM customer c
INNER JOIN address a ON c.address_id = a.address_id
INNER JOIN city ci ON a.city_id = ci.city_id
INNER JOIN country co ON ci.country_id = co.country_id
ORDER BY co.country, ci.city, c.last_name
LIMIT 50;
-- Explicación: Solo muestra clientes con información geográfica completa


-- =====================================================================
-- SECCIÓN 2: LEFT JOIN (5 consultas)
-- Muestra TODOS los registros de la tabla izquierda + coincidencias
-- =====================================================================

-- 106. Todas las películas y sus actores (LEFT JOIN)
-- Muestra TODAS las películas, incluso las que NO tienen actores
SELECT 
    f.film_id,
    f.title AS pelicula,
    a.first_name,
    a.last_name
FROM film f
LEFT JOIN film_actor fa ON f.film_id = fa.film_id
LEFT JOIN actor a ON fa.actor_id = a.actor_id
ORDER BY f.title
LIMIT 100;
-- Explicación: Si una película no tiene actores, aparece con NULL en nombre/apellido
-- Útil para identificar películas sin actores asignados


-- 107. Todos los clientes y sus pagos (LEFT JOIN)
-- Muestra TODOS los clientes, incluso los que NO han pagado
SELECT 
    c.customer_id,
    c.first_name,
    c.last_name,
    c.email,
    p.payment_id,
    p.amount,
    p.payment_date
FROM customer c
LEFT JOIN payment p ON c.customer_id = p.customer_id
ORDER BY c.last_name, c.first_name
LIMIT 100;
-- Explicación: Clientes sin pagos aparecen con NULL en payment_id, amount, etc.
-- Útil para encontrar clientes que nunca han comprado


-- 108. Todas las películas y sus alquileres (LEFT JOIN)
-- Muestra TODAS las películas, incluso las NUNCA alquiladas
SELECT 
    f.film_id,
    f.title AS pelicula,
    f.release_year,
    r.rental_id,
    r.rental_date
FROM film f
LEFT JOIN inventory i ON f.film_id = i.film_id
LEFT JOIN rental r ON i.inventory_id = r.inventory_id
ORDER BY f.title
LIMIT 100;
-- Explicación: Películas nunca alquiladas tienen NULL en rental_id
-- Útil para análisis de películas poco populares


-- 109. Todos los actores y sus películas de acción (LEFT JOIN)
-- Muestra TODOS los actores, incluso los que NO actúan en acción
SELECT 
    a.actor_id,
    a.first_name,
    a.last_name,
    f.title AS pelicula_accion
FROM actor a
LEFT JOIN film_actor fa ON a.actor_id = fa.actor_id
LEFT JOIN film f ON fa.film_id = f.film_id
LEFT JOIN film_category fc ON f.film_id = fc.film_id
LEFT JOIN category cat ON fc.category_id = cat.category_id AND cat.name = 'Action'
WHERE cat.name = 'Action' OR cat.name IS NULL
ORDER BY a.last_name, a.first_name
LIMIT 100;
-- Explicación: Actores sin películas de acción tienen NULL en pelicula_accion


-- 110. Todas las categorías y sus películas clasificadas PG (LEFT JOIN)
-- Muestra TODAS las categorías, incluso sin películas PG
SELECT 
    cat.category_id,
    cat.name AS categoria,
    f.title AS pelicula_pg,
    f.rating
FROM category cat
LEFT JOIN film_category fc ON cat.category_id = fc.category_id
LEFT JOIN film f ON fc.film_id = f.film_id AND f.rating = 'PG'
ORDER BY cat.name, f.title
LIMIT 100;
-- Explicación: Categorías sin películas PG muestran NULL en pelicula_pg
-- Útil para ver qué categorías no tienen contenido familiar


-- =====================================================================
-- SECCIÓN 3: RIGHT JOIN (5 consultas)
-- Muestra coincidencias + TODOS los registros de la tabla derecha
-- =====================================================================

-- 111. Pagos y todos los clientes (RIGHT JOIN)
-- Muestra algunos pagos y TODOS los clientes
SELECT 
    p.payment_id,
    p.amount,
    p.payment_date,
    c.customer_id,
    c.first_name,
    c.last_name
FROM payment p
RIGHT JOIN customer c ON p.customer_id = c.customer_id
ORDER BY c.last_name, c.first_name
LIMIT 100;
-- Explicación: Clientes sin pagos aparecen con NULL en payment_id
-- Similar al LEFT JOIN pero la tabla de referencia es la derecha


-- 112. Actores de películas y todas las películas (RIGHT JOIN)
-- TODAS las películas aparecen, con o sin actores
SELECT 
    a.first_name,
    a.last_name,
    f.film_id,
    f.title AS pelicula
FROM actor a
RIGHT JOIN film_actor fa ON a.actor_id = fa.actor_id
RIGHT JOIN film f ON fa.film_id = f.film_id
ORDER BY f.title
LIMIT 100;
-- Explicación: Películas sin actores tienen NULL en first_name y last_name
-- El enfoque está en mostrar TODAS las películas


-- 113. Alquileres y todo el inventario (RIGHT JOIN)
-- Muestra TODO el inventario, con o sin alquileres
SELECT 
    r.rental_id,
    r.rental_date,
    i.inventory_id,
    f.title AS pelicula,
    i.store_id
FROM rental r
RIGHT JOIN inventory i ON r.inventory_id = i.inventory_id
RIGHT JOIN film f ON i.film_id = f.film_id
ORDER BY i.inventory_id
LIMIT 100;
-- Explicación: Items de inventario nunca alquilados tienen NULL en rental_id
-- Útil para encontrar copias que ocupan espacio sin generar ingresos


-- 114. Ciudades con clientes y todas las ciudades (RIGHT JOIN)
-- Muestra TODAS las ciudades, tengan o no clientes
SELECT 
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS cliente,
    ci.city_id,
    ci.city,
    co.country
FROM customer c
RIGHT JOIN address a ON c.address_id = a.address_id
RIGHT JOIN city ci ON a.city_id = ci.city_id
RIGHT JOIN country co ON ci.country_id = co.country_id
ORDER BY co.country, ci.city
LIMIT 100;
-- Explicación: Ciudades sin clientes tienen NULL en customer_id y cliente
-- Útil para expansión de mercado: ver ciudades sin presencia


-- 115. Categorías de películas y todas las películas (RIGHT JOIN)
-- TODAS las películas, con o sin categoría asignada
SELECT 
    cat.name AS categoria,
    f.film_id,
    f.title AS pelicula,
    f.rating
FROM category cat
RIGHT JOIN film_category fc ON cat.category_id = fc.category_id
RIGHT JOIN film f ON fc.film_id = f.film_id
ORDER BY f.title
LIMIT 100;
-- Explicación: Películas sin categoría tienen NULL en categoria
-- Útil para encontrar películas sin clasificar


-- =====================================================================
-- SECCIÓN 4: FULL OUTER JOIN (5 consultas)
-- MySQL no soporta FULL JOIN, se simula con UNION de LEFT y RIGHT
-- Muestra TODO: coincidencias + no coincidencias de AMBAS tablas
-- =====================================================================

-- 116. FULL JOIN: Todas las películas y todos los actores (simulado)
-- Muestra películas sin actores Y actores sin películas
SELECT 
    f.film_id,
    f.title AS pelicula,
    a.actor_id,
    a.first_name,
    a.last_name
FROM film f
LEFT JOIN film_actor fa ON f.film_id = fa.film_id
LEFT JOIN actor a ON fa.actor_id = a.actor_id

UNION

SELECT 
    f.film_id,
    f.title AS pelicula,
    a.actor_id,
    a.first_name,
    a.last_name
FROM film f
RIGHT JOIN film_actor fa ON f.film_id = fa.film_id
RIGHT JOIN actor a ON fa.actor_id = a.actor_id
ORDER BY pelicula, last_name
LIMIT 150;
-- Explicación: Muestra TODO el catálogo
-- - Películas sin actores (NULL en actor)
-- - Actores sin películas (NULL en pelicula)
-- - Relaciones existentes


-- 117. FULL JOIN: Todos los clientes y todos los pagos
-- Clientes sin pagos Y pagos sin cliente válido (si existieran)
SELECT 
    c.customer_id,
    c.first_name,
    c.last_name,
    p.payment_id,
    p.amount
FROM customer c
LEFT JOIN payment p ON c.customer_id = p.customer_id

UNION

SELECT 
    c.customer_id,
    c.first_name,
    c.last_name,
    p.payment_id,
    p.amount
FROM customer c
RIGHT JOIN payment p ON c.customer_id = p.customer_id
ORDER BY customer_id, payment_id
LIMIT 150;
-- Explicación: Panorama completo de la relación cliente-pago
-- Identifica clientes sin actividad Y pagos huérfanos (datos inconsistentes)


-- 118. FULL JOIN: Inventario y alquileres completo
-- TODO el inventario y TODOS los alquileres
SELECT 
    i.inventory_id,
    f.title AS pelicula,
    r.rental_id,
    r.rental_date
FROM inventory i
LEFT JOIN film f ON i.film_id = f.film_id
LEFT JOIN rental r ON i.inventory_id = r.inventory_id

UNION

SELECT 
    i.inventory_id,
    f.title AS pelicula,
    r.rental_id,
    r.rental_date
FROM inventory i
RIGHT JOIN film f ON i.film_id = f.film_id
RIGHT JOIN rental r ON i.inventory_id = r.inventory_id
ORDER BY inventory_id, rental_date
LIMIT 150;
-- Explicación: Auditoría completa
-- - Inventario nunca alquilado
-- - Alquileres de inventario eliminado (inconsistencias)


-- 119. FULL JOIN: Categorías y películas completo
-- TODAS las categorías y TODAS las películas
SELECT 
    cat.category_id,
    cat.name AS categoria,
    f.film_id,
    f.title AS pelicula
FROM category cat
LEFT JOIN film_category fc ON cat.category_id = fc.category_id
LEFT JOIN film f ON fc.film_id = f.film_id

UNION

SELECT 
    cat.category_id,
    cat.name AS categoria,
    f.film_id,
    f.title AS pelicula
FROM category cat
RIGHT JOIN film_category fc ON cat.category_id = fc.category_id
RIGHT JOIN film f ON fc.film_id = f.film_id
ORDER BY categoria, pelicula
LIMIT 150;
-- Explicación: Revisión completa del catálogo
-- - Categorías vacías (sin películas)
-- - Películas sin categoría


-- 120. FULL JOIN: Ciudades y clientes completo
-- TODAS las ciudades y TODOS los clientes
SELECT 
    ci.city_id,
    ci.city,
    co.country,
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS cliente
FROM city ci
LEFT JOIN country co ON ci.country_id = co.country_id
LEFT JOIN address a ON ci.city_id = a.city_id
LEFT JOIN customer c ON a.address_id = c.address_id

UNION

SELECT 
    ci.city_id,
    ci.city,
    co.country,
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS cliente
FROM city ci
RIGHT JOIN country co ON ci.country_id = co.country_id
RIGHT JOIN address a ON ci.city_id = a.city_id
RIGHT JOIN customer c ON a.address_id = c.address_id
ORDER BY country, city, cliente
LIMIT 150;
-- Explicación: Análisis geográfico completo
-- - Ciudades sin clientes (oportunidades de expansión)
-- - Clientes sin ciudad válida (datos a corregir)


-- =====================================================================
-- CASOS DE USO PRÁCTICOS DE CADA JOIN
-- =====================================================================

-- =====================================================================
-- FIN DEL DOCUMENTO
-- =====================================================================