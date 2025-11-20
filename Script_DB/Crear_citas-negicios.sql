CREATE TABLE IF NOT EXISTS public.negocios
(
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    nombre_negocio VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    horario VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(255),

    CONSTRAINT fk_negocio_user
        FOREIGN KEY (id_user)
        REFERENCES public.users(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'estado_cita') THEN
        CREATE TYPE estado_cita AS ENUM ('PENDIENTE', 'ACEPTADA', 'RECHAZADA', 'CANCELADA');
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS public.citas
(
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL,
    id_negocio INTEGER NOT NULL,
    razon VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado estado_cita NOT NULL,

    CONSTRAINT fk_cita_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES public.users(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_cita_negocio
        FOREIGN KEY (id_negocio)
        REFERENCES public.negocios(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

WITH admin_user AS (
    SELECT id FROM public.users WHERE role = 'ADMINISTRADOR' LIMIT 1
)

INSERT INTO public.negocios (id_user, nombre_negocio, descripcion, horario, direccion, telefono)
VALUES
(
    (SELECT id FROM admin_user),
    'Barbería Elegante',
    'Servicio de cortes de cabello y barbería profesional',
    '9:00 AM - 7:00 PM',
    'Calle 10 #12-34',
    '3005551122'
),
(
    (SELECT id FROM admin_user),
    'Consultorio Dental Sonrisa',
    'Consultas odontológicas y limpiezas',
    '8:00 AM - 5:00 PM',
    'Av. Central #45-90',
    '3008884466'
);

WITH cliente_user AS (
    SELECT id FROM public.users WHERE role = 'CLIENTE' LIMIT 1
),
negocios_list AS (
    SELECT id FROM public.negocios ORDER BY id ASC LIMIT 2
)

INSERT INTO public.citas (id_cliente, id_negocio, razon, fecha, hora, estado)
VALUES
(
    (SELECT id FROM cliente_user),
    (SELECT id FROM negocios_list LIMIT 1),
    'Corte de cabello y arreglo de barba',
    CURRENT_DATE + INTERVAL '1 day',
    '10:00',
    'PENDIENTE'
),
(
    (SELECT id FROM cliente_user),
    (SELECT id FROM negocios_list OFFSET 1 LIMIT 1),
    'Consulta de revisión dental',
    CURRENT_DATE + INTERVAL '2 day',
    '14:30',
    'PENDIENTE'
);

SELECT * FROM public.negocios ORDER BY id ASC;
SELECT * FROM public.citas ORDER BY id ASC;

