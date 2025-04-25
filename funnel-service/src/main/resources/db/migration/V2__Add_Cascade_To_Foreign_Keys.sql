ALTER TABLE class_names DROP FOREIGN KEY fk_funnel_id;
ALTER TABLE funnel_pages DROP FOREIGN KEY fk_funnel_p_id;

ALTER TABLE class_names
    ADD CONSTRAINT fk_funnel_id
        FOREIGN KEY (funnel_id)
            REFERENCES funnels (id)
            ON DELETE CASCADE;

ALTER TABLE funnel_pages
    ADD CONSTRAINT fk_funnel_p_id
        FOREIGN KEY (funnel_id)
            REFERENCES funnels (id)
            ON DELETE CASCADE;