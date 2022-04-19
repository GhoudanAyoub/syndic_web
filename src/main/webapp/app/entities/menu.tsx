import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/personne">
        <Translate contentKey="global.menu.entities.personne" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/resident">
        <Translate contentKey="global.menu.entities.resident" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/syndic">
        <Translate contentKey="global.menu.entities.syndic" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/immeuble">
        <Translate contentKey="global.menu.entities.immeuble" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/appartement">
        <Translate contentKey="global.menu.entities.appartement" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/revenu">
        <Translate contentKey="global.menu.entities.revenu" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/depense">
        <Translate contentKey="global.menu.entities.depense" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/categorie">
        <Translate contentKey="global.menu.entities.categorie" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/payement">
        <Translate contentKey="global.menu.entities.payement" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
