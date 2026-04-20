package ru.bmstu.iu3.service;

/**
 * Источник актуального каталога меню из справочника (сервис B).
 */
public interface MenuCatalogSource {

    /**
     * @return false если справочник недоступен
     */
    boolean ensureCatalogLoaded();
}
