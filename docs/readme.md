# CPASS REPORT ENGINE
Report Engine component for CPass. Creates a Servlet endpoint for report processing.\
Implementation note: the report processing runtime chosen is [Eclipse BIRT](https://www.eclipse.org/birt/).

## Development
The separation of concerns regarding the modules MUST be respected:
- `/web` contains the Servlet entrypoints to the application
- `/ear` contains the packaging logic for an EAR archive
- `/tar` contains the packaging logic for an TAR archive, for automated distribution

## Configuration
In case a new profile is to be added, it MUST be referenced in the `<profiles>` section of the `pom.xml`, and the corresponding `properties` file MUST be added in the `/profiles` folder.

### Properties configuration
- jpa.dataSource: the datasource JNDI name
- jpa.showSql: whether the SQL should be shown
- jpa.formatSql: whether the shown SQL should be formatted (only used if `jpa.showSql=true`)
- birt.working.folder: the folder containing the BIRT template files
