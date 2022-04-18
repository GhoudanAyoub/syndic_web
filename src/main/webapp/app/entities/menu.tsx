import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/personne">
        Personne
      </MenuItem>
      <MenuItem icon="asterisk" to="/resident">
        Resident
      </MenuItem>
      <MenuItem icon="asterisk" to="/syndic">
        Syndic
      </MenuItem>
      <MenuItem icon="asterisk" to="/immeuble">
        Immeuble
      </MenuItem>
      <MenuItem icon="asterisk" to="/appartement">
        Appartement
      </MenuItem>
      <MenuItem icon="asterisk" to="/revenu">
        Revenu
      </MenuItem>
      <MenuItem icon="asterisk" to="/depense">
        Depense
      </MenuItem>
      <MenuItem icon="asterisk" to="/categorie">
        Categorie
      </MenuItem>
      <MenuItem icon="asterisk" to="/payement">
        Payement
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
