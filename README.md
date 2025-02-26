# **Ubivis Generic System (UGS) Plugin**

## **Overview**

The **Ubivis Generic System (UGS) Plugin** is a **Minecraft Paper Plugin** designed for **modular extension loading**. It provides a simple interface that allows dynamically loading and executing custom extensions in the form of `.jar` files without requiring a server restart.

## **Features**

- **Command-based execution**: Run installed extensions with `/ugs <extension> <parameters>`.
- **Dynamic extension loading**: Load new `.jar` files by placing them in the `extensions/` folder and using `/ugs reload`.
- **Custom parameter handling**: Each extension can define its own required parameters.
- **Error handling and logging**: Logs are stored in `logs/load-log.txt`.
- **No configuration file required**: Simply add extensions to the designated folder.

## **Installation**

1. Download the latest release of the plugin.
2. Place the `UGS.jar` file into your server’s `plugins/` folder.
3. Start or reload the server to enable the plugin.

## **Usage**

### **Available Commands**

- `/ugs <extension> <parameter1> <parameter2> ...`\
  Runs a specified extension with the provided parameters.
- `/ugsreload`\
  Reloads the extension directory, adding new `.jar` extensions dynamically.

## **File Structure**

```
/plugins/UGS/
│── extensions/               # Folder for storing .jar extensions
│   ├── Random.jar            # Example extension
│   ├── MySpecialFunction.jar # Another example extension
│── logs/                     # Folder for storing logs
│   ├── load-log.txt          # Logs of loaded extensions
```

## **Creating Extensions**

To create an extension for UGS, implement the `ExtensionInterface`:

```java
public interface ExtensionInterface {
    String getName();
    String getDescription();
    String execute(String[] params);
    int getParameterCount();
}
```

Example `Random.jar` implementation:

```java
public class RandomExtension implements ExtensionInterface {
    @Override
    public String getName() { return "Random"; }
    
    @Override
    public String getDescription() { return "Generates a random number within a range"; }
    
    @Override
    public String execute(String[] params) {
        if (params.length < 2) return "Missing parameters: Min and Max";
        int min = Integer.parseInt(params[0]);
        int max = Integer.parseInt(params[1]);
        return String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
    }

    @Override
    public int getParameterCount() { return 2; }
}
```

## **Future Enhancements**

- **Admin permissions**: Restrict certain commands to server operators.
- **Web-based extension management**: Upload and manage extensions via a web interface.
- **API Enhancements**: Provide more data interaction for extensions.

## **License**

This project is open-source and available under the MIT License.

---

For any issues or feature requests, please submit a ticket on the repository. 🚀

